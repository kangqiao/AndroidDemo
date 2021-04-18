package com.zp.androidx.base

import android.os.Environment
import android.util.Log
import android.view.ViewConfiguration
import dalvik.system.DexClassLoader
import java.lang.reflect.Array
import java.text.MessageFormat

/**
 * Created by zhaopan on 4/14/21
 */
object SysUtils {

    const val TAG = "SysUtils"

    /**
     * 打印当前线程的调用堆栈
     *
     */
    @JvmStatic
    fun printTrack() {
        val st = Thread.currentThread().stackTrace
        if (st == null) {
            println("无堆栈...")
            return
        }
        val sbf = StringBuffer()
        for (e in st) {
            if (sbf.length > 0) {
                sbf.append(" <- ")
                sbf.append(System.getProperty("line.separator"))
            }
            sbf.append(
                MessageFormat.format(
                    "{0}.{1}() {2}", e.className, e.methodName, e.lineNumber
                )
            )
        }
        Log.i(TAG, sbf.toString())
    }

    /**
     * 要注入的dex的路径
     * @param path
     */
    fun inject(path: String) {
        try {
            //获取classes的dexElements
            val cl = Class.forName("dalvik.system.BaseDexClassLoader")
            val pathList = getField(cl, "pathList", javaClass.classLoader)
            val baseElements = getField(pathList.javaClass, "dexElements", pathList)

            //获取patch_dex的dexElements(需要先加载dex)
            val dexopt = getDir("dexopt", 0)
            val dexClassLoader = DexClassLoader(path, dexopt, dexopt, javaClass.classLoader)
            val obj = getField(cl, "pathList", dexClassLoader)
            val dexElements = getField(obj.javaClass, "dexElements", obj)

            //合并两个Element
            val combineElements = combineArray(dexElements, baseElements);

            //将合并后的Element数组重新赋值给app的ClassLoader
            setField(pathList.javaClass, "dexElements", pathList, combineElements)

            //======== 以下是测试是否成功注入 =================
            val objTest = getField(pathList.javaClass, "dexElements", pathList)
            Log.e(TAG, "BugFixApplication length = ${Array.getLength(objTest)}")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
    }

    private fun getDir(dir: String, option: Int): String {
        return Environment.getExternalStorageDirectory().absolutePath.plus("/${dir}")
    }

    /**
     * 通过反射合并两个数组
     */
    fun combineArray(firstArr: Any, secondArr: Any): Any {
        val firstLen = Array.getLength(firstArr)
        val secondLen = Array.getLength(secondArr)
        val length = firstLen + secondLen
        val componentType = firstArr.javaClass.componentType
        val newArr = Array.newInstance(componentType, length)
        for (i in 0 until length) {
            if (i < firstLen) {
                Array.set(newArr, i, Array.get(firstArr, i))
            } else {
                Array.set(newArr, i, Array.get(secondArr, i-firstLen))
            }
        }
        return newArr
    }

    /**
     * 通过反射获取对象的属性值
     */
    fun getField(cl: Class<*>, fieldName: String, obj: Any): Any {
        val field = cl.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(obj)
    }

    /**
     * 通过反射设置对象的属性值
     */
    fun setField(cl: Class<*>, fieldName: String, obj: Any, value: Any) {
        val field = cl.getDeclaredField(fieldName)
        field.isAccessible = true
        field.set(obj, value)
    }
}