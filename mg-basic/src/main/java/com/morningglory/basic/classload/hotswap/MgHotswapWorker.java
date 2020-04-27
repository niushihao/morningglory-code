package com.morningglory.basic.classload.hotswap;

import com.morningglory.basic.classload.Demo;
import com.morningglory.basic.concurrent.thread.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * @Author: qianniu
 * @Date: 2020-04-17 13:22
 * @Desc:
 */
@Slf4j
public class MgHotswapWorker {

    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10,new NamedThreadFactory("hotswap"));

    public static void main(String[] args) throws IOException, InterruptedException {


        Runnable runnable = () -> {
            Demo demo = new Demo();
            demo.sayHi();
        };
        executor.scheduleAtFixedRate(runnable,0,20, TimeUnit.SECONDS);


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

        WatchKey key = null;
        while ((key = watchService.take()) != null){
            for (WatchEvent<?> event : key.pollEvents()) {
                //监听事件类型
                WatchEvent.Kind<?> kind = event.kind();
                //得到监听文件的路径(文件名)
                Path name =(Path) event.context();
                //获取全路径 path + name
                Path allPath = ((Path) key.watchable()).resolve(name);
                if(allPath.toFile().isDirectory()){
                    continue;
                }
                log.info("kind = {}, name = {}, allPath = {}",kind,name,allPath);
                executor.execute(new Worker(allPath));
            }
            key.reset();
        }

    }


    public static class Worker implements Runnable{

        private Path path;

        public Worker(Path path) {
            this.path = path;
        }

        @Override
        public void run() {
            try {
                MgHotswapClassLoader classLoader = new MgHotswapClassLoader();
                Demo demo = (Demo) classLoader.loadClass(path, false).newInstance();
                demo.sayHi();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }
    }
}
