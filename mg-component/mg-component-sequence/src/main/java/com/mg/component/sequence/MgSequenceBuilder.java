package com.mg.component.sequence;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * @author qianniu
 * @date 2022/8/15
 * @desc
 */
@Data
@Accessors(chain = true)
public class MgSequenceBuilder {

    private String name;

    private List<DataSource> dataSourceList;

    private String config;

    public MgSequence build(){
        // 从配置文件读
        String config = getConfigInner();

        ServiceLoader<MgSequenceFactory> load = ServiceLoader.load(MgSequenceFactory.class);

        Iterator<MgSequenceFactory> iterator = load.iterator();
        while (iterator.hasNext()){
            MgSequenceFactory next = iterator.next();
            MgSequence sequence = next.create(this);
            if(Objects.nonNull(sequence)){
                sequence.init();
                return sequence;
            }
        }
        throw new RuntimeException();
    }



    private String getConfigInner() {
        return null;
    }
}
