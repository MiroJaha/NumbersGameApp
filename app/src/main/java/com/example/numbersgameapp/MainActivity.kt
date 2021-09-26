package com.example.numbersgameapp

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.view.Gravity
import android.widget.Button

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.set
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private val randomNumber= Random.nextInt(11)

    private fun playAgain(){

        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage("The Correct Answer was $randomNumber \nWould You Like To Play Again:")
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    _, _ -> this.recreate()
            })

        val alert=dialogBuilder.create()
        alert.setTitle("Game Over!!")
        alert.show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val note=findViewById<ConstraintLayout>(R.id.mainL)
        val myRV=findViewById<RecyclerView>(R.id.rvMain)
        val button=findViewById<Button>(R.id.BGuss)
        val entry=findViewById<EditText>(R.id.Entry)

        var countGussies=4
        val list= arrayListOf<String>()

        button.setOnClickListener {
            try {
                var number = entry.text.toString().toInt()

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

                    myRV.adapter = RecyclerViewAdapter(list)
                    myRV.layoutManager = LinearLayoutManager(this)

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
}