package org.jinghouyu.json.utils;


/**
 * 分层键值对，其建之间是分层的，比如Class对象，其父类或者父接口比它的层次要高。
 * 他旨在提供一种快速的搜索方法，根据键快速的搜索到适合的值。
 * 所谓适合的值，若在低层找不到对应的值，则去高层找！
 * 
 * Hierarchical key-value pair, its key is hierarchical, such as instance of Class, its parent class object or interface object 
 * has a higher level than itself.
 * Hierarchical key-value pair is aimed at providing a high-speed searching method to find the most suitable value according key.
 * the most suitable value, that is, it will find it level-by-level from low to high
 * 
 * @author jinghouyu
 *
 * @param <K>
 * @param <V>
 */
public class HierarchicalMap<K, V> {	
}
