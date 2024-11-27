package com.yiverthdevs.dentalcare

import CitaViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

class Asistente_virtual_fragment : Fragment() {

    private lateinit var model: GenerativeModel
    var extractedText: String = ""
    private var isPdfLoaded = mutableStateOf(false) // Nuevo estado para cargar el PDF

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inicializa PDFBox
        PDFBoxResourceLoader.init(requireContext())

        return ComposeView(requireContext()).apply {
            setContent {
                // Pasar la instancia de la interfaz a la función composable
                geminiAssistant(model, extractedText, isPdfLoaded.value)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar los parámetros necesarios
        val apiKey = ApiKeyProvider.API_KEY

        model = GenerativeModel(
            modelName = "gemini-1.5-pro", // Verifica que el nombre del modelo sea correcto
            apiKey = apiKey,
            generationConfig = generationConfig {
                temperature = 0.5f // Controla la creatividad del modelo
                topK = 50 // Limita el numero de palabras candidatas consideradas en cada paso de generacion
                topP = 0.9f // Controla la nucleus sampling, Considera todas las plabras con alta probabilidad en cada paso
                maxOutputTokens = 500 // Controla la logitud maxima de la respuesta
            },
            safetySettings = listOf(
                SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE),
            )
        )
        downloadPdfFromFirebase()
    }
    // Función para descargar el PDF desde Firebase Storage
    private fun downloadPdfFromFirebase() {
        val storage = Firebase.storage
        val storageRef = storage.reference.child("pdfs/informacion_dentalCare.pdf")
        val localFile = File.createTempFile("informacion_dentalCare", ".pdf")

        storageRef.getFile(localFile)
            .addOnSuccessListener {
                Log.d("Firebase", "PDF descargado: ${localFile.absolutePath}")
                // Extraer el texto del PDF y almacenarlo en una variable
                extractedText = extractTextFromPdf(localFile)
                isPdfLoaded.value = true // Actualizar el estado cuando el PDF esté listo
                // Aquí puedes proceder a leer o mostrar el PDF
            }
            .addOnFailureListener { exception ->
                Log.e("Firebase", "Error al descargar PDF: ${exception.message}")
            }
    }
    private fun extractTextFromPdf(file: File): String {
        var text = ""
        try {
            // Carga el documento PDF
            val document = PDDocument.load(file)
            // Crea un extractor de texto
            val pdfStripper = PDFTextStripper()
            // Extrae el texto del documento
            text = pdfStripper.getText(document)
            // Cierra el documento
            document.close()
        } catch (e: IOException) {
            Log.e("PDFBox", "Error al extraer texto del PDF: ${e.message}")
        }
        return text
    }
}


@Composable
fun geminiAssistant(model: GenerativeModel, extractedText: String, isPdfLoaded: Boolean) {
    var userInput by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var isLoading by remember { mutableStateOf(false) }

    // Lista de mensajes en formato de burbuja (usuario y asistente)
    val messages = remember { mutableStateListOf<Pair<String, Boolean>>() }
    var isLoadingMessage by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            state = listState,
            reverseLayout = true
        ) {
            // Se agrega un espacio de 60.dp en la parte superior para evitar el choque con el menú

            items(messages) { message ->
                ChatBubble(
                    message = message.first,
                    isUserMessage = message.second,
                    isLoading = message.first.isEmpty() && !message.second // Muestra carga si es de gemini y no tiene mensaje
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            if (isLoading){
                item {
                    ChatBubble(
                        message = "Cargando...", isUserMessage = false
                    )
                }
            }
        }

        // Caja de entrada para el mensaje
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray.copy(alpha = 0.3f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = green
                ),
                shape = RoundedCornerShape(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (userInput.isNotBlank()) {
                                val userMessage = userInput
                                // Se agrega el mensaje del usuario a la lista
                                messages.add(0,Pair(userMessage, true))
                                userInput = "" // Limpiar la caja de texto
                                messages.add(0,Pair("", false))
                                isLoadingMessage = true

                                // Enviar la pregunta a Gemini y recibir la respuesta
                                coroutineScope.launch {
                                    try {
                                        val queryWithPdfContext = "Basado en el siguiente contexto:\n$extractedText\n\nPregunta: $userMessage"
                                        Log.d("ContextQuery", queryWithPdfContext) // Para verificar si el contexto se agrega correctamente
                                        val response = model.generateContent(queryWithPdfContext)
                                        withContext(Dispatchers.Main) {
                                            val geminiResponse = response.text ?: "Lo siento en este momento no puedo generar una respuesta"
                                            val cleanedResponse = cleanedResponse(geminiResponse)

                                            // Agregar la respuesta de Gemini a la lista
                                            messages[0]=Pair(cleanedResponse,false)
                                        }
                                        listState.animateScrollToItem(messages.size - 1)
                                    } catch (e: Exception) {
                                        messages[0]=Pair("error: ${e.message}",false)
                                    }finally {
                                        isLoadingMessage = false
                                    }
                                }
                            }
                        },
                        enabled = isPdfLoaded, // Habilitar botón solo si PDF está cargado
                        modifier = Modifier
                            .size(48.dp)
                            .background(green, shape = RoundedCornerShape(24.dp))
                    )  {
                        Icon(
                            painter = painterResource(id = R.drawable.button_send_assistant),
                            contentDescription = "Enviar",
                            tint = Color.White
                        )
                    }
        }
    }
}

fun cleanedResponse(response: String):String {
    return response.replace(Regex("\\*+|\\-+"),"").trim()
}

@Composable
fun ChatBubble(message: String, isUserMessage: Boolean, isLoading: Boolean = false) {
    val bubbleColor = if (isUserMessage) green else Color.LightGray.copy(alpha = 0.3f)
    val alignment = if (isUserMessage) Arrangement.End else Arrangement.Start
    val bubbleShape = RoundedCornerShape(16.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        horizontalArrangement = alignment
    ) {
        Box(
            modifier = Modifier
                .background(bubbleColor, bubbleShape)
                .padding(8.dp)
                .widthIn(max = 280.dp)
        ) {
            if (isLoading){
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    color = green
                )
            }else {
                Text(
                    text = message,
                    color = if (isUserMessage) Color.White else Color.Black
                )
            }
        }
    }
}
