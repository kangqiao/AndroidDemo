package com.zp.plugin.dellog

import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * Created by zhaopan on 2020-07-27
 */
class DelLogPlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {
        System.out.println("zp:::: ===== Zhaopan DelLogPlugin =====")
        project.extensions.create('dellogExtension', DelLogExtension);

        project.afterEvaluate {
            //在gradle 构建完之后执行
            project.logger.error("zp::: dellogExtension1 : " + project.dellogExtension.sourceDir);
            println "zp::: dellogExtension1 : " + project.dellogExtension.sourceDir

            def rootDir = project.projectDir.toString().plus(project.dellogExtension.sourceDir);

            project.logger.error(rootDir);

            DelLogUtil.delLog(new File(rootDir));
        }

        project.task('dellog', {
            project.logger.error("zp::: dellogExtension2 : " + project.dellogExtension.sourceDir);
            println "zp::: dellogExtension2 : " + project.dellogExtension.sourceDir

            def rootDir = project.projectDir.toString().plus(project.dellogExtension.sourceDir);

            project.logger.error(rootDir);

            DelLogUtil.delLog(new File(rootDir));

        })


    }
}