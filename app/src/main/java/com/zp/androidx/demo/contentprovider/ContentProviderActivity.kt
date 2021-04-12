package com.zp.androidx.demo.contentprovider

import android.content.ContentUris
import android.content.ContentValues
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zp.androidx.demo.databinding.ActivityContentProviderTestBinding
import java.lang.StringBuilder

/**
 * Created by zhaopan on 4/12/21
 */
class ContentProviderActivity : AppCompatActivity() {
    companion object {
        const val URI = "content://${CustomContentProvider.AUTHORITY}/person"
    }

    private lateinit var binding: ActivityContentProviderTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContentProviderTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnQuery.setOnClickListener {
            val cursor = contentResolver.query(Uri.parse(URI), null, null, null, null)
            cursor?.let {
                binding.txtContent.text = StringBuilder().run {
                    while (it.moveToNext()) {
                        append(it.getString(cursor.getColumnIndex("name"))).append("\n")
                    }
                    toString()
                }
            }
        }

        binding.btnInsert.setOnClickListener {
            val name = binding.etName.text.toString()
            val values = ContentValues()
            values.put("name", name)
            val result = contentResolver.insert(Uri.parse(URI), values)
            //注意: 此条添加上, ContentObserver才可以监听数据库改变
            contentResolver.notifyChange(Uri.parse(URI), null);
            val parseId = result?.let { it1 -> ContentUris.parseId(it1) }
            if (parseId != null && parseId > 0) {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
                binding.etName.text.clear()
            }
        }

        val handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                binding.txtContent.text = msg.obj as CharSequence?
            }
        }

        contentResolver.registerContentObserver(Uri.parse("content://${CustomContentProvider.AUTHORITY}/person"),
            true,
            object : ContentObserver(handler) {
                override fun onChange(selfChange: Boolean) {
                    super.onChange(selfChange)
                    //Toast.makeText(this@ContentProviderActivity, "改变1", Toast.LENGTH_LONG).show()
                }

                override fun onChange(selfChange: Boolean, uri: Uri?) {
                    super.onChange(selfChange, uri)
                    Toast.makeText(this@ContentProviderActivity, "改变2"+uri?.toString()+Thread.currentThread().toString(), Toast.LENGTH_LONG).show()
                    uri?.let {changeUri ->
                        val cursor = contentResolver.query(changeUri, null, null, null, null)
                        cursor?.let {
                            binding.txtContent.text = StringBuilder().run {
                                while (it.moveToNext()) {
                                    append(it.getString(cursor.getColumnIndex("name"))).append("\n")
                                }
                                toString()
                            }
                        }
                    }
                }
            })
    }
}