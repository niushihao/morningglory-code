package jsr269.processor;

import jsr269.annotation.MgAllArgsConstructor;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

import static jsr269.processor.ProcessUtil.*;

/**
 * @author qianniu
 * @date 2020/10/28 1:20 下午
 * @desc
 */
@SupportedAnnotationTypes("jsr269.annotation.MgAllArgsConstructor")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MgAllArgsConstructorProcessor extends BaseProcessor{

    /**
     * 字段的语法树节点的集合
     */
    private List<JCTree.JCVariableDecl> fieldJCVariables;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // 首先获取被AllArgsConstructor注解标记的元素
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(MgAllArgsConstructor.class);
        set.forEach(element -> {
            // 获取当前元素的JCTree对象
            JCTree jcTree = trees.getTree(element);

            //JCTree利用的是访问者模式，将数据与数据的处理进行解耦，TreeTranslator就是访问者，这里我们重写访问类时的逻辑
            jcTree.accept(new TreeTranslator(){

                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClass){
                    messager.printMessage(Diagnostic.Kind.NOTE
                            ,"@MgAllArgsConstructor process [" + jcClass.name.toString() + "] begin!");

                    before(jcClass);

                    //添加全参构造方法
                    if (!hasAllArgsConstructor(fieldJCVariables, jcClass)) {
                        jcClass.defs = jcClass.defs.append(
                                createAllArgsConstructor()
                        );
                    }

                    after();

                    messager.printMessage(Diagnostic.Kind.NOTE, "process class [" + jcClass.name.toString() + "], end");

                }
            });
        });
        return false;
    }

    /**
     * 创建全参数构造方法
     *
     * @return 全参构造方法语法树节点
     */
    private JCTree createAllArgsConstructor() {
        ListBuffer<JCTree.JCStatement> jcStatements = new ListBuffer<>();
        for(JCTree.JCVariableDecl jcVariable : fieldJCVariables){
            // 添加构造方法的赋值语句 " this.xxx = xxx; "
            jcStatements.append(
                    treeMaker.Exec(
                            treeMaker.Assign(
                                    treeMaker.Select(
                                            treeMaker.Ident(names.fromString(THIS)),
                                            names.fromString(jcVariable.name.toString())
                                    ),
                                    treeMaker.Ident(names.fromString(jcVariable.name.toString()))
                            )
                    )
            );
        }
        JCTree.JCBlock jcBlock = treeMaker.Block(
                0 //访问标志
                , jcStatements.toList() //所有的语句
        );

        return treeMaker.MethodDef(
                treeMaker.Modifiers(Flags.PUBLIC), //访问标志
                names.fromString(CONSTRUCTOR_NAME), //名字
                treeMaker.TypeIdent(TypeTag.VOID), //返回类型
                List.nil(), //泛型形参列表
                cloneJCVariablesAsParams(treeMaker, fieldJCVariables), //参数列表
                List.nil(), //异常列表
                jcBlock, //方法体
                null //默认方法（可能是interface中的那个default）
        );

    }

    /**
     * 进行一些初始化工作
     *
     * @param jcClass 类的语法树节点
     */
    private void before(JCTree.JCClassDecl jcClass) {
        this.fieldJCVariables = getJCVariables(jcClass);
    }

    /**
     * 进行一些清理工作
     */
    private void after() {
        this.fieldJCVariables = null;
    }
}
