/**
 * 
 * ASM是一个Java字节码操纵框架，它能被用来动态生成类或者增强既有类的功能。
 * ASM可以直接产生二进制class文件，也可以在类被加载入Java虚拟机之前动态改变类行为。
 * Java class被存储在严格格式定义的.class文件里，这些类文件拥有足够的元数据来解析类中的所有元素：类名称、方法、属性以及 Java 字节码（指令）。
 * ASM从类文件中读入信息后，能够改变类行为，分析类信息，甚至能够根据用户要求生成新类。
 * 目前许多框架如cglib、Hibernate、Spring都直接或间接地使用ASM操作字节码
 * https://www.cnblogs.com/clds/p/4985893.html
 * @author dzw
 *
 * 阅读顺序：
 * 
 * 1：ASMGettingStarted
 * 
 * 2：ASMifierTest
 * 
 * 3：DecCls
 * 
 * 4：TraceClassVisitorTest
 */
package reflect.proxy.asm;