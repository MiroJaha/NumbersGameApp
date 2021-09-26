package com.example.numbersgameapp

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private var randomNumber= Random.nextInt(11)
    private lateinit var note : ConstraintLayout
    private lateinit var myRV : RecyclerView
    private lateinit var button : Button
    private lateinit var entry : EditText

    private var countGussies = 4
    private lateinit var list : ArrayList<String>


    private fun playAgain(){

        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage("The Correct Answer was $randomNumber \nWould You Like To Play Again:")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ -> this.recreate() }
            //.setNegativeButton("No"){dialog,_ -> dialog.cancel()}

        val alert=dialogBuilder.create()
        alert.setTitle("Game Over!!")
        alert.show()

    }


    private fun show(str:ArrayList<String>){
        myRV.adapter = RecyclerViewAdapter(str)
        myRV.layoutManager = LinearLayoutManager(this)
        if(str.size!=0)
            myRV.smoothScrollToPosition(str.size-1)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState!=null){
            countGussies= savedInstanceState.getInt("countGussies", 0)
            list = savedInstanceState.getStringArrayList("RecycleView")!!
            randomNumber = savedInstanceState.getInt("random",0)
        }
        else
            list= arrayListOf()

        note=findViewById(R.id.mainL)
        myRV=findViewById(R.id.rvMain)
        button=findViewById(R.id.BGuss)
        entry=findViewById(R.id.Entry)

        if(countGussies<=0){
            countGussies=4
            list= arrayListOf()
            randomNumber= Random.nextInt(11)
        }
        else
            show(list)

        button.setOnClickListener {
            try {
                val number = entry.text.toString().toInt()

                if(number<0||number>10){
                    Snackbar.make(note, "Please Enter Number between 0 and 10 Only", Snackbar.LENGTH_LONG).show()
                    entry.text=null
                }
                else {
                    if (number == randomNumber) {
                        list.add("Congratulation Your Guss was Correct!!")
                        countGussies = 0
                    } else {
                        countGussies--
                        list.add(
                            "Sorry $number is Wrong Guss\nYou Guessed ${4 - countGussies} Times\n" +
                                    "You Have $countGussies Gussies Left")
                    }

                    show(list)

                    entry.text = null
                }
                if(countGussies<=0)
                    playAgain()
            }
            catch (e:Exception) {
                Snackbar.make(note, "Please Enter Valid Number", Snackbar.LENGTH_LONG).show()
                entry.text=null
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("countGussies", countGussies)
        outState.putStringArrayList("RecycleView", list)
        outState.putInt("random",randomNumber)

    }
}