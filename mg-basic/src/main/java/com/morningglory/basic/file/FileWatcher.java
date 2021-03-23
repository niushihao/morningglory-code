package com.morningglory.basic.file;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * @Author: qianniu
 * @Date: 2020-04-17 19:51
 * @Desc: 文件变动监听器
 * 1. 通过 FileSystems.getDefault().newWatchService() 创建 WatchService
 * 2. 通过 ClassLoader.getSystemResource("").getPath() 获取当前calssPath 根路径
 * 3. 通过 Files.walkFileTree 遍历目录 将目录和子目录祖册到 WatchService
 * 4.
 *
 */
@Slf4j
public class FileWatcher {

    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(ClassLoader.getSystemResource("").getPath());

        // 遍历文件,当遇到目录时 祖册到WatchService
        Files.walkFileTree(path,new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                    throws IOException{
                Objects.requireNonNull(dir);
                if (exc != null){
                    throw exc;
                }

                dir.register(watchService,ENTRY_MODIFY);
                log.info("path : {} 注册成功",dir.toString());
                return FileVisitResult.CONTINUE;
            }
        });

        Runnable runnable = () ->{
            try {
                WatchKey key = null;
                log.info("start");
                while ((key = watchService.take()) != null){
                    for (WatchEvent<?> event : key.pollEvents()) {
                        //监听事件类型
                        WatchEvent.Kind<?> kind = event.kind();
                        //得到监听文件的路径(文件名)
                        Path name =(Path) event.context();
                        //获取全路径 path + name
                        Path allPath = ((Path) key.watchable()).resolve(name);
                        log.info("kind = {}, name = {}, allPath = {}",kind,name,allPath);
                    }
                    key.reset();
                }
            }catch (Exception e){

            }
        };

        new Thread(runnable,"MG-watchThread").start();

    }


}
