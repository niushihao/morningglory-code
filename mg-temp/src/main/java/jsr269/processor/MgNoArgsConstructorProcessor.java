package jsr269.processor;

import jsr269.annotation.MgNoArgsConstructor;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

import static jsr269.processor.ProcessUtil.CONSTRUCTOR_NAME;
import static jsr269.processor.ProcessUtil.hasNoArgsConstructor;

/**
 * @author qianniu
 * @date 2020/10/28 12:59 下午
 * @desc
 */
@SupportedAnnotationTypes("jsr269.annotation.MgNoArgsConstructor")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MgNoArgsConstructorProcessor extends BaseProcessor{
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // 首先获取被 MgNoArgsConstructor 注解标记的元素
        Set<? extends Element> set = roundEnv
                .getElementsAnnotatedWith(MgNoArgsConstructor.class);
        set.forEach(element -> {
            // 获取当前元素的JCTree对象
            JCTree jcTree = trees.getTree(element);

            //JCTree利用的是访问者模式，将数据与数据的处理进行解耦，TreeTranslator就是访问者，这里我们重写访问类时的逻辑
            jcTree.accept(new TreeTranslator(){
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClass){
                    messager.printMessage(Diagnostic.Kind.NOTE
                            ,"@MgNoArgsConstructor process [" + jcClass.name.toString() + "] begin!");

                    // 添加无参构造方法
                    if(!hasNoArgsConstructor(jcClass)){
                        jcClass.defs.append(createNoArgsConstructor());
                    }

                    messager.printMessage(Diagnostic.Kind.NOTE, "@MgNoArgsConstructor process [" + jcClass.name.toString() + "] end!");
                }
            });

        });


        return false;
    }

    /**
     * 创建无参构造方法
     * @return
     */
    private JCTree createNoArgsConstructor() {

        JCTree.JCBlock jcBlock = treeMaker.Block(
                0,          // 访问标志
                List.nil());    // 所有语句

        return treeMaker.MethodDef(
                treeMaker.Modifiers(Flags.PUBLIC),      // 访问标志
                names.fromString(CONSTRUCTOR_NAME),     // 名字
                treeMaker.TypeIdent(TypeTag.VOID),      //返回类型
                List.nil(),                             //泛型形参列表
                List.nil(),                             //参数列表
                List.nil(),                             //异常列表
                jcBlock,                                //方法体
                null                       //默认方法（可能是interface中的那个default

        );
    }
}
