package com.example.practicalexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class SecondScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)

        val btnLogout : Button = findViewById(R.id.btnlogout)
        btnLogout.setOnClickListener{
            val artdialogbuilder = AlertDialog.Builder(this@SecondScreen)
            artdialogbuilder.setMessage("Are You Sure to Exit?")
            artdialogbuilder.setTitle("Title is Here")
            artdialogbuilder.setCancelable(false)
            artdialogbuilder.setPositiveButton("Yes"){_,_ ->
                finish()
            }
            artdialogbuilder.setNegativeButton("No"){_,_ ->
                Toast.makeText(this@SecondScreen, "Clicked No",Toast.LENGTH_SHORT).show()
            }
            artdialogbuilder.setNeutralButton("Cancel"){_,_ ->
                Toast.makeText(this@SecondScreen, "Clicked Cancel",Toast.LENGTH_SHORT).show()
            }
            val alertDialogBox = artdialogbuilder.create()
            alertDialogBox.show()
        }

        val myDB = UserDbHelper(this)

        val idEdit : EditText = findViewById(R.id.inputId)
        val nameEdit : EditText = findViewById(R.id.inputName)
        val ageEdit : EditText = findViewById(R.id.inputAge)
        val emailEdit : EditText = findViewById(R.id.inputEmail)

        val addButton : Button = findViewById(R.id.btnadd)
        val showButton : Button = findViewById(R.id.btnshow)
        val updateButton : Button = findViewById(R.id.btnupdate)
        val deleteButton : Button = findViewById(R.id.btndelete)
        val showAllButton : Button = findViewById(R.id.btnshowall)
        val deleteAllButton : Button = findViewById(R.id.btndeleteall)

        addButton.setOnClickListener {
            val isInserted = myDB.insertData(nameEdit.text.toString(), ageEdit.text.toString(), emailEdit.text.toString())
            if (isInserted) {
                Toast.makeText(this, "Data Inserted...", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show()
            }
        }

        showButton.setOnClickListener {
            val id = idEdit.text.toString()
            if (id.isEmpty()) {
                idEdit.error = "Please Enter Id"
                return@setOnClickListener
                //stopping the execution flow for the OnClickListener
            }
            val cursor = myDB.getData(id)
            var data: String? = null
            if (cursor != null) {
                if (cursor.moveToNext()) {
                    data = ("ID : " + cursor.getString(0) + "\n" +
                            "NAME : " + cursor.getString(1) + "\n" +
                            "AGE : " + cursor.getString(2) + "\n" +
                            "EMAIL : " + cursor.getString(3) + "\n")
                    showMessage("DATA", data)
                } else {
                    showMessage("DATA", "There is no Data")
                }
            }
        }

        updateButton.setOnClickListener {
            val isUpdated = myDB.updateData(
                idEdit.text.toString(),
                nameEdit.text.toString(),
                ageEdit.text.toString(),
                emailEdit.text.toString()
            )
            if (isUpdated) {
                Toast.makeText(this, "Data Updated Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

        deleteButton.setOnClickListener {
            val id = idEdit.text.toString()
            if (id.isEmpty()) {
                idEdit.error = "Please Enter Id"
                return@setOnClickListener
            }
            val varResult = myDB.deleteData(id)
            if (varResult > 0) {
                Toast.makeText(this, "Data Deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Deletion Error", Toast.LENGTH_SHORT).show()
            }
        }

        showAllButton.setOnClickListener {
            val cursor = myDB.getAllData()
            val buffer = StringBuffer()
            if (cursor != null) {
                if (cursor.count == 0) {
                    showMessage("Data", "Nothing found")
                    return@setOnClickListener
                }
            }
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    buffer.append("ID : " + cursor.getString(0) + "\n")
                    buffer.append("NAME : " + cursor.getString(1) + "\n")
                    buffer.append("AGE : " + cursor.getString(2) + "\n")
                    buffer.append("EMAIL : " + cursor.getString(3) + "\n\n")
                }
            }
            showMessage("DATA", buffer.toString())
        }


        deleteAllButton.setOnClickListener {
            val varResult = myDB.deleteAllData()
            if (varResult > 0) {
                Toast.makeText(this, "All Data has been deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Deletion Error", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showMessage(title: String, msg: String) {
        val builder = AlertDialog.Builder(this)
        builder.create()
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.show()

    }
}