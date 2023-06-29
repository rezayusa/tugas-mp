package com.reza.tugasmp

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.reza.tugasmp.adapter.UserAdapter
import com.reza.tugasmp.data.AppDatabase
import com.reza.tugasmp.data.entity.User

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private  var list  = mutableListOf<User>()
    private lateinit var adapter: UserAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView=findViewById(R.id.recyler_view)
        fab= findViewById(R.id.fab)

        database= AppDatabase.getInstance(applicationContext)
        adapter = UserAdapter(list)
        adapter.setDialog(object : UserAdapter.Dialog{
            override fun onClick(position: Int) {
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle(list[position].fullName)
                dialog.setItems(R.array.item_option,DialogInterface.OnClickListener{dialog,which ->
                    when (which) {
                        0 -> {
                //                        ubah
                            val intent = Intent(this@MainActivity,TambahActivity::class.java)
                            intent.putExtra("id",list[position].uid)
                            startActivity(intent)
                        }
                        1 -> {
                //                        hapus
                            database.userDao().delete(list[position])
                            getData()
                        }
                        else -> {
                //                        batal
                            dialog.dismiss()
                        }
                    }
                })
                val dialogView =dialog.create()
                dialogView.show()
            }

        })

        recyclerView.adapter =adapter
        recyclerView.layoutManager= LinearLayoutManager(applicationContext, VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, VERTICAL))

        fab.setOnClickListener{
            startActivity(Intent(this,TambahActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun getData(){
        list.clear()
        list.addAll(database.userDao().getAll())
        adapter.notifyDataSetChanged()
    }
}
//REZA YULI SANTOSA
//STI202102480