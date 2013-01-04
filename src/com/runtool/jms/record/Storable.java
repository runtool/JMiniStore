package com.runtool.jms.record;

import java.io.Serializable;

/**
 * 该接口用于序列化对象，转化成可以存储
 * 如果对象可以存储到JMiniStore中，需要实现该接口
 * @author runtool
 *
 */
public interface Storable extends Serializable{

}
