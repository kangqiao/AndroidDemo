package com.zp.androidx.bindview_processor

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import com.zp.androidx.bindview_annotation.BindView
import java.text.MessageFormat
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.Elements


class BindViewProcessor : AbstractProcessor() {

    private lateinit var elementUtils: Elements

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        elementUtils = processingEnv.elementUtils
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val hashSet: MutableSet<String> = HashSet()
        hashSet.add(BindView::class.java.canonicalName)
        return hashSet
    }
    override fun process(set: MutableSet<out TypeElement>, roundEnvironment: RoundEnvironment): Boolean {
        //获取所有包含 BindView 注解的元素
        val elementSet: Set<out Element>  = roundEnvironment.getElementsAnnotatedWith(BindView::class.java)
        val typeElementMapHashMap = mutableMapOf<TypeElement, MutableMap<Int, VariableElement>>()
        for (element in elementSet) {
            //因为 BindView 的作用对象是 FIELD，因此 element 可以直接转化为 VariableElement
            (element as? VariableElement)?.apply {
                //getEnclosingElement 方法返回封装此 Element 的最里层元素
                //如果 Element 直接封装在另一个元素的声明中，则返回该封装元素
                //此处表示的即 Activity 类对象
                if (typeElementMapHashMap[enclosingElement] == null) {
                    typeElementMapHashMap[enclosedElements as TypeElement] = mutableMapOf()
                }
                //获取注解值，即 ViewId
                val bindAnnotation = getAnnotation(BindView::class.java)
                typeElementMapHashMap[enclosingElement]?.set(bindAnnotation.value, this)
            }
        }

        for (map in typeElementMapHashMap) {
            val packageName = ElementUtils.getPackageName(elementUtils, map.key)
            val javaFile = JavaFile.builder(packageName, generateCodeByPoet(map.key, map.value)).build()
            javaFile.writeTo(processingEnv.filer)
        }
        return false
    }

    /**
     * 生成 Java 类
     *
     * @param typeElement        注解对象上层元素对象，即 Activity 对象
     * @param variableElementMap Activity 包含的注解对象以及注解的目标对象
     * @return
     */
    private fun generateCodeByPoet(
        typeElement: TypeElement,
        variableElementMap: MutableMap<Int, VariableElement>
    ): TypeSpec{
        //自动生成的文件以 Activity名 + ViewBinding 进行命名
        return TypeSpec.classBuilder(ElementUtils.getEnclosingClassName(typeElement) + "BindView")
            .addModifiers(Modifier.PUBLIC)
            .addMethod(generateMethodByPoet(typeElement, variableElementMap))
            .build();
    }

    /**
     * 生成方法
     *
     * @param typeElement        注解对象上层元素对象，即 Activity 对象
     * @param variableElementMap Activity 包含的注解对象以及注解的目标对象
     * @return
     */
    private fun generateMethodByPoet(
        typeElement: TypeElement,
        variableElementMap: MutableMap<Int, VariableElement>
    ): MethodSpec {
        val className = ClassName.bestGuess(typeElement.qualifiedName.toString())
        val parameter = "_" + StringUtils.toLowerCaseFirstChar(className.simpleName())
        val methodBuilder = MethodSpec.methodBuilder("bind")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(Void::class.java)
            .addParameter(className, parameter)
        for (map in variableElementMap) {
            //被注解的字段名
            val name = map.value.simpleName.toString()
            //被注解的字段的对象类型的全名称
            val type = map.value.asType().toString()
            val text = "{0}.{1}=({2})({3}.findViewById({4}));"
            methodBuilder.addCode(MessageFormat.format(text, parameter, name, type))
        }
        return methodBuilder.build();
    }

}