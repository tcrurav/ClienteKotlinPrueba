package com.example.clientedeprueba

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONArray
import com.android.volley.VolleyError
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        buttonSendRequest.setOnClickListener {
            requestToServer()
        }

        imageViewMelenara.setOnClickListener {
            var intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun requestToServer(){

        // Instantiate the RequestQueue with the cache and network. Start the queue.
        val queue = Volley.newRequestQueue(this)

        val url = "http://192.168.103.99:40000/api/students/1"

        /*val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                textViewMessage.text = "Response: %s".format(response.toString())
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
            }
        )
*/
        val answer = JSONObject("""{"name":"test name", "age":25}""")
        var jsonArray = JSONArray()
        jsonArray.put(answer)

        println(jsonArray)

        val jsonArrayRequest = object: JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response: JSONArray ->

                for(i in 0..(response.length() - 1)){
                    textViewMessage.text = textViewMessage.text.toString() + response.getJSONObject(i).getString("name")
                }

                /*textViewMessage.text = "Response: %s".format(response.toString())*/
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
            }) {

            override fun getBodyContentType(): String {
                return "application/json"
            }

            override fun getBody(): ByteArray {
                var params2 = HashMap<String, String>()
                params2.put("Login","your credentials" )
                params2.put("Password", "your credentials")
                return JSONObject(params2 as Map<*, *>).toString().toByteArray()
            }
        }



        // Add the request to the RequestQueue.
        /*queue.add(jsonObjectRequest)*/
        queue.add(jsonArrayRequest)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
