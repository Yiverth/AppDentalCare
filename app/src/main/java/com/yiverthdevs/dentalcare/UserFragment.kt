package com.yiverthdevs.dentalcare

import android.media.Image
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth


class UserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                UserScreenContent()
            }
        }
    }
}
val green = Color(0xFF009E0F)
@Composable
fun UserScreenContent(userViewModel: UserViewModel = viewModel()){
    val user by userViewModel.user.observeAsState()

    user?.let {
        UserProfileScreen(it, userViewModel)
    }?: run {
        Text("Cargando informacion del usuario...")
    }
}

@Composable
fun UserProfileScreen(userData: UserData, userViewModel: UserViewModel= viewModel()) {

    var showDialog by remember { mutableStateOf(false) } // Estado para mostrar o no el dialogo
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {

            uri: Uri? ->
        uri?.let {
            // Guarda la imagen seleccionada en firebase y actualiza la URL en la base de datos
            userViewModel.uploadImage(uri)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(80.dp))

        // Circulo para la imagen del perfil o icono predeterminado
        Card(
            shape = androidx.compose.foundation.shape.CircleShape,
            backgroundColor = green,
            modifier = Modifier
                .size(200.dp)
                .clickable { showDialog = true } // Mostrar dialogo hacer click
        ) {
            // Condicion para mostrar la imagen o el icono predeterminado
            if (userData.photoUrl.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(userData.photoUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        //Condicion para el icono de nombre
        val namePainter = if (userData.gender == "Masculino") {
            painterResource(id = R.drawable.user_name_male)
        } else {
            painterResource(id = R.drawable.user_name_female)
        }
        userInfoItem(
            painter = namePainter,
            info = userData.name)

        Spacer(modifier = Modifier.height(8.dp))

        userInfoItem(
            painter = painterResource(id = R.drawable.user_email),
            info = FirebaseAuth.getInstance().currentUser?.email?: "")

        Spacer(modifier = Modifier.height(8.dp))

        userInfoItem(
            painter = painterResource(id = R.drawable.button_nav_phone),
            info = userData.number)

        Spacer(modifier = Modifier.height(8.dp))

        // Condicion para el icono de genero
        val genderPainter = if (userData.gender == "Masculino") {
            painterResource(id = R.drawable.user_gender_male)
        } else {
            painterResource(id = R.drawable.user_gender_female)
        }
        userInfoItem(
            painter = genderPainter,
            info = userData.gender)
    }
    // Dialogo que se muestra cuando showDialogo es verdadero
    if (showDialog) {
        imageDialog(
            imageUrl = userData.photoUrl,
            onDismiss = {showDialog= false},
            onChangeImage = {launcher.launch("image/*")
            showDialog= false} // Ocultar el dialogo despues de seleccionar una nueva imagen
        )
    }
}
@Composable
fun imageDialog(imageUrl: String, onDismiss: () -> Unit, onChangeImage: () -> Unit) =
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (imageUrl.isEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(300.dp)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(250.dp)
                        .clip(CircleShape)
                        .background(green)
                ) {
                    // Icono predeterminado si no hay una URL de imagen
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(250.dp)
                            .padding(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onChangeImage() },
                colors = ButtonDefaults.buttonColors(backgroundColor = green),
                shape = CircleShape,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.7f) // Ancho
                    .height(50.dp) // Altura
                ) {
                Text(
                    text = "Cambiar imagen",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
@Composable
fun userInfoItem(painter: Painter, info: String) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 16.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                colorFilter = ColorFilter.tint(green),
                modifier = Modifier
                    .size(35.dp)
                    .padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "|",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
            Text(
                text = info,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun userProfileScreenPreview() {
    UserProfileScreen(
        UserData(
            userId = "12345",
            name = "Yiverth Martinez",
            number = "147852369",
            gender = "masculino",
            photoUrl = ""
        )
    )
}