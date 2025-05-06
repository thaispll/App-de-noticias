package br.com.codeschool.myapplication

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.codeschool.myapplication.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btPublicarNoticia.setOnClickListener{
            val titulo = binding.editTituloNoticia.text.toString()
            val noticia = binding.editNoticia.text.toString()
            val data = binding.editDataNoticia.text.toString()
            val autor = binding.editAutorNoticia.text.toString()

            if (titulo.isEmpty() || noticia.isEmpty() || data.isEmpty() || autor.isEmpty()){
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            } else {
                salvarNoticia(titulo, noticia, data, autor)
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun salvarNoticia(titulo: String, noticia: String, data: String, autor: String){

        val mapNoticias = hashMapOf(
            "titulo" to titulo,
            "noticia" to noticia,
            "data" to data,
            "autor" to autor
        )
        db.collection("noticias").document("noticia")
            .set(mapNoticias).addOnCompleteListener{ tarefa ->
                if (tarefa.isSuccessful){
                    Toast.makeText(this, "Notícia publicada com sucesso!", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{

            }
    }
}