package com.zp.plugin.dellog

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.gradle.api.Project

public class PreDexTransform extends Transform {

    Project project
    // 添加构造，为了方便从plugin中拿到project对象，待会有用
    public PreDexTransform(Project project) {
        this.project = project
    }

    // Transfrom在Task列表中的名字
    // TransfromClassesWithPreDexForXXXX
    @Override
    String getName() {
        return "preDex"
    }

    // 指定input的类型
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    // 指定Transfrom的作用范围
    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider, boolean isIncremental)
            throws IOException, TransformException, InterruptedException {

        // inputs就是输入文件的集合
        // outputProvider可以获取outputs的路径
    }
}

