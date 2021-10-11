package com.example.headsup

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etTaboo1: EditText
    private lateinit var etTaboo2: EditText
    private lateinit var etTaboo3: EditText

    private lateinit var btAdd: Button
    private lateinit var btBack: Button

    private val apiInterface by lazy { APIClient().getClient().create(APIInterface::class.java) }

    private lateinit var progressDialog: ProgressDialog

    private lateinit var existingCelebrities: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)


        existingCelebrities = intent.extras!!.getStringArrayList("celebrityNames")!!

        etName = findViewById(R.id.etNewName)
        etTaboo1 = findViewById(R.id.etNewTaboo1)
        etTaboo2 = findViewById(R.id.etNewTaboo2)
        etTaboo3 = findViewById(R.id.etNewTaboo3)

        btAdd = findViewById(R.id.btNewAdd)
        btAdd.setOnClickListener {
            if(etName.text.isNotEmpty() && etTaboo1.text.isNotEmpty() &&
                etTaboo2.text.isNotEmpty() && etTaboo3.text.isNotEmpty()){
                addCelebrity()
            }else{
                Toast.makeText(this, "One or more fields is empty", Toast.LENGTH_LONG).show()
            }
        }

        btBack = findViewById(R.id.btNewBack)
        btBack.setOnClickListener {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun addCelebrity(){
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        progressDialog.show()

        apiInterface.addCelebrity(
            Celebrity(
                // This only takes care of the first name
                // If Michael Jordan exists, Michael jordan can still be added
                // Can you find a way to fix this?
                etName.text.toString().capitalize(),
                etTaboo1.text.toString(),
                etTaboo2.text.toString(),
                etTaboo3.text.toString(),
                0
            )
        ).enqueue(object: Callback<Celebrity> {
            override fun onResponse(call: Call<Celebrity>, response: Response<Celebrity>) {
                progressDialog.dismiss()
                if(!existingCelebrities.contains(etName.text.toString().lowercase())){
                    intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@AddActivity, "Celebrity Already Exists", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Celebrity>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@AddActivity, "Unable to get data", Toast.LENGTH_LONG).show()
            }
        })
    }
}