package simple.init.load;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.oracle.tools.packager.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import simple.config.Process;
import simple.exception.ProcessException;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.*;

/**
 * @author qianniu
 * @date 2020/9/21 8:58 下午
 * @desc
 */
@Component
@Slf4j
public class FileSystemProcessDefinitionLoader implements ProcessDefinitionLoader{

    private static final List<Process> LIST_OF_PROCESS = new ArrayList<>();
    private static final String DEFAULT_PROCESS_FILE_PATH = "PROCESS-FLOW/process_flow_*.json";
    @Value("${process.flow.custom.location:PROCESS-FLOW/custom_process_flow_*.json}")
    private String customPath;
    private final ResourcePatternResolver fileResolver = new PathMatchingResourcePatternResolver();
    private Set<Process> definedProcesses;

    @Override
    public Collection<Process> loadProcessDefinition() {
        if (!CollectionUtils.isEmpty(this.definedProcesses)) {
            return this.definedProcesses;
        }
        log.info("[START_LOAD_PROCESS_FLOWS] custom path = {}", this.customPath);
        try {
            List<Process> defaultProcess = this.loadProcessConfig("PROCESS-FLOW/process_flow_*.json");
            List<Process> customProcess = this.loadProcessConfig(this.customPath);
            Set<Process> allProcess = Sets.newHashSet(customProcess);
            allProcess.addAll(defaultProcess);
            this.definedProcesses = Collections.unmodifiableSet(allProcess);
            return this.definedProcesses;

        }catch (Exception e){
            log.error("load process config fail, custom path : {} ,cause : {}", this.customPath, Throwables.getStackTraceAsString(e));
            throw new ProcessException("load process config fail");
        }

    }

    private List<Process> loadProcessConfig(String resourcePath) throws Exception{
        List<Process> processes = Lists.newArrayList();
        Resource[] resources = this.fileResolver.getResources("classpath*:" + resourcePath);
        if (resources != null) {
            int length = resources.length;

            for(int i = 0; i < length; ++i) {
                Resource resource = resources[i];
                String json = StreamUtils.convertStreamToString(resource.getInputStream());

                log.debug("json = {}",json);
                processes.addAll(JSON.parseArray(json,Process.class));
            }
        }
        return processes;
    }

    public static void main(String[] args) throws Exception {
        FileSystemProcessDefinitionLoader loader = new FileSystemProcessDefinitionLoader();
        List<Process> processes = loader.loadProcessConfig("PROCESS-FLOW/custom_process_flow_*.json");
        log.info(processes.toString());

    }
}
