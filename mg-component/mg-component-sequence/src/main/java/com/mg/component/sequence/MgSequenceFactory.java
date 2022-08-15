package com.mg.component.sequence;

/**
 * @author qianniu
 * @date 2022/8/15
 * @desc
 */
public interface MgSequenceFactory {

    MgSequence create(MgSequenceBuilder builder);
}
