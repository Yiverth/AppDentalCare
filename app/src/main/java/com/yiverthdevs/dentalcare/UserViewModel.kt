package com.yiverthdevs.dentalcare

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class UserViewModel : ViewModel() {
    // Instancia de Firebase Firestore para acceder a la base de datos
    private val db = FirebaseFirestore.getInstance()

    // Instancia de Firebase Authentication para la autenticación de usuarios
    private val auth = FirebaseAuth.getInstance()

    // Instancia para acceder al storage de firebase
    private  val storage = FirebaseStorage.getInstance()

    // Para manejar la lista de los usuarios
    private val _user = MutableLiveData<UserData>()
    val user: LiveData<UserData> = _user

    // Variable temporal para almacenar la URL de la imagen seleccionada
    private var localImageUri: String? = null

    init {
        fetchUserData()
    }

    private fun fetchUserData(){
        val user = auth.currentUser
        if(user!=null){
            viewModelScope.launch(Dispatchers.IO){
                try {
                    // Filtra las citas por el user id del usuario autenticado
                    val document = db.collection("users")
                        .document( user.uid) // Filtra por el userId
                        .get()
                        .await()

                    val userData = document.toObject(UserData::class.java)
                    _user.postValue(userData)
                }catch (e:Exception){
                    _user.postValue(null)

                }
            }
        }
    }

    fun uploadImage (uri: Uri) {
        val user = auth.currentUser
        if(user!=null){

            // Almacenar la URL local temporalmente
            localImageUri = uri.toString()
            _user.value = _user.value?.copy(photoUrl = localImageUri!!)

            val stogerRef = storage.reference.child("userImage/${user.uid}/${UUID.randomUUID()}")

            viewModelScope.launch(Dispatchers.IO){
                try {
                    val uploadTask = stogerRef.putFile(uri).await()
                    val downtLoadUrl = uploadTask.storage.downloadUrl.await().toString()
                    db.collection("users").document( user.uid).update("photoUrl",downtLoadUrl).await()

                    // Limpier la URL temporal y actualizar el live data con la nueva url de firebase
                    localImageUri = null
                    _user.postValue(_user.value?.copy(photoUrl = downtLoadUrl))
                }catch (e:Exception){
                    Log.d("userViewModel","Error al cargar imagen",e)
                }
            }
        }
    }
}