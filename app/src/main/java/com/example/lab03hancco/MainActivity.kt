package com.example.lab03hancco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab03hancco.ui.theme.Lab03hanccoTheme
import kotlinx.coroutines.launch

private val Ink = Color(0xFF172554)
private val Blue = Color(0xFF3157D5)
private val Mint = Color(0xFF67E8C2)
private val PageBackground = Color(0xFFF5F7FC)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { Lab03hanccoTheme(dynamicColor = false) { Lab03App() } }
    }
}

private enum class LabPage(val title: String) {
    Welcome("Inicio"), Explorer("Componentes"), Exercise("Ejercicio")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Lab03App() {
    var page by remember { mutableStateOf(LabPage.Welcome) }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        containerColor = PageBackground,
        topBar = { TopAppBar(title = { Text("LAB 03  ·  COMPOSE", fontWeight = FontWeight.Bold, letterSpacing = 1.sp) }) },
        snackbarHost = { SnackbarHost(snackbar) },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                LabPage.entries.forEach { item ->
                    NavigationBarItem(
                        selected = page == item,
                        onClick = { page = item },
                        icon = { Icon(if (item == LabPage.Welcome) Icons.Default.Home else if (item == LabPage.Explorer) Icons.Default.Menu else Icons.Default.Favorite, contentDescription = item.title) },
                        label = { Text(item.title) }
                    )
                }
            }
        },
        floatingActionButton = {
            if (page != LabPage.Welcome) ExtendedFloatingActionButton(
                onClick = { scope.launch { snackbar.showSnackbar("¡Sigue explorando Jetpack Compose!") } },
                icon = { Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null) },
                text = { Text("Probar") }
            )
        }
    ) { padding ->
        when (page) {
            LabPage.Welcome -> WelcomePage(Modifier.padding(padding))
            LabPage.Explorer -> ExplorerPage(Modifier.padding(padding))
            LabPage.Exercise -> ExercisePage(Modifier.padding(padding))
        }
    }
}

@Composable
private fun WelcomePage(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("Fabricio") }
    var greeting by remember { mutableStateOf("¡Qué bueno tenerte aquí!") }
    var accepted by remember { mutableStateOf<Boolean?>(null) }
    LazyColumn(modifier.fillMaxSize(), contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
        item { WelcomeHero() }
        item {
            Card(shape = RoundedCornerShape(26.dp), colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.fillMaxWidth().padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("TU PRIMERA COMPOSICIÓN", style = MaterialTheme.typography.labelMedium, color = Blue, fontWeight = FontWeight.Bold, letterSpacing = 1.4.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("Bienvenido al curso", style = MaterialTheme.typography.headlineSmall, color = Ink, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(8.dp))
                    Text(greeting, style = MaterialTheme.typography.bodyLarge, color = Color(0xFF667085), textAlign = TextAlign.Center)
                    Spacer(Modifier.height(18.dp))
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Escribe tu nombre") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = { greeting = if (name.isBlank()) "¡Hola, estudiante!" else "¡Hola, ${name.trim()}! Qué bueno tenerte aquí." }, modifier = Modifier.fillMaxWidth()) { Text("Mostrar saludo") }
                    Spacer(Modifier.height(14.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(onClick = { accepted = true }, modifier = Modifier.weight(1f)) { Text("Aceptar") }
                        TextButton(onClick = { accepted = false }, modifier = Modifier.weight(1f)) { Text("Ahora no") }
                    }
                    accepted?.let { Text(if (it) "¡Excelente, empecemos!" else "Puedes volver cuando quieras.", color = Blue, modifier = Modifier.padding(top = 8.dp)) }
                }
            }
        }
        item { Text("Construida con Column, Row, Text, TextField, Button, Image y Spacer.", color = Color(0xFF667085), style = MaterialTheme.typography.bodySmall) }
    }
}

@Composable
private fun WelcomeHero() {
    Box(Modifier.fillMaxWidth().clip(RoundedCornerShape(30.dp)).background(Brush.linearGradient(listOf(Ink, Blue, Color(0xFF7296FF)))).padding(24.dp)) {
        Column(Modifier.fillMaxWidth()) {
            Text("PROGRAMACIÓN EN MÓVILES", color = Mint, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, letterSpacing = 1.2.sp)
            Spacer(Modifier.height(14.dp))
            Text("Diseña.\nCompón.\nSorprende.", color = Color.White, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.ExtraBold, lineHeight = 38.sp)
            Spacer(Modifier.height(10.dp))
            Text("Layouts y elementos componibles en Android", color = Color.White.copy(alpha = .82f))
            Spacer(Modifier.height(18.dp))
            CourseImage(Modifier.width(220.dp).height(160.dp).clip(RoundedCornerShape(22.dp)))
        }
    }
}

@Composable
private fun ComposeIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val w = size.width; val h = size.height
        drawRoundRect(Color.White.copy(alpha = .16f), topLeft = Offset(w * .2f, h * .05f), size = androidx.compose.ui.geometry.Size(w * .62f, h * .9f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * .12f))
        drawRoundRect(Color.White, topLeft = Offset(w * .28f, h * .14f), size = androidx.compose.ui.geometry.Size(w * .46f, h * .65f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * .07f))
        drawCircle(Blue, radius = w * .12f, center = Offset(w * .51f, h * .4f))
        drawLine(Mint, Offset(w * .38f, h * .61f), Offset(w * .61f, h * .61f), strokeWidth = w * .035f, cap = StrokeCap.Round)
        drawLine(Color(0xFFB8C7FF), Offset(w * .38f, h * .69f), Offset(w * .57f, h * .69f), strokeWidth = w * .035f, cap = StrokeCap.Round)
    }
}

@Composable
private fun CourseImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.course_art),
        contentDescription = "Ilustración de una pantalla de aprendizaje móvil",
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

private data class ComponentInfo(val name: String, val description: String)

private val containerItems = listOf(
    ComponentInfo("LazyColumn", "Lista vertical con scroll y carga eficiente."), ComponentInfo("LazyRow", "Colección horizontal que se recorre de lado."),
    ComponentInfo("Grid / LazyVerticalGrid", "Distribuye elementos en una cuadrícula desplazable."), ComponentInfo("ConstraintLayout", "Posiciona elementos con relaciones entre sí."),
    ComponentInfo("Scaffold", "Estructura de pantalla con barras, FAB y contenido."), ComponentInfo("Surface", "Superficie con color, forma y elevación."),
    ComponentInfo("Chip", "Acción compacta o selección rápida."), ComponentInfo("BackdropScaffold", "Patrón de fondo y panel frontal; aquí se muestra su equivalente visual en Material 3."),
    ComponentInfo("FlowRow", "Coloca elementos en filas y salta de línea."), ComponentInfo("FlowColumn", "Coloca elementos en columnas y salta de columna.")
)
private val controlItems = listOf(
    ComponentInfo("AlertDialog", "Diálogo de confirmación con título y acciones."), ComponentInfo("Card", "Agrupa contenido en una superficie elevada."),
    ComponentInfo("Checkbox", "Permite activar varias opciones."), ComponentInfo("FloatingActionButton", "Acción principal que flota sobre el contenido."),
    ComponentInfo("Icon", "Refuerza el significado de una acción."), ComponentInfo("Image", "Muestra el recurso gráfico de la tarjeta."),
    ComponentInfo("ProgressBar", "Indica progreso circular o lineal."), ComponentInfo("RadioButton", "Permite elegir una sola opción."),
    ComponentInfo("Slider", "Selecciona un valor dentro de un rango."), ComponentInfo("Spacer", "Añade una separación explícita."),
    ComponentInfo("Switch", "Activa o desactiva una preferencia."), ComponentInfo("TopAppBar", "Barra superior con título y acciones.")
)
private val moreItems = listOf(
    ComponentInfo("BottomNavigation / NavigationBar", "Navegación entre secciones en la parte inferior."), ComponentInfo("Dialog", "Ventana modal sencilla."),
    ComponentInfo("Divider", "Separa visualmente contenido relacionado."), ComponentInfo("DropdownMenu", "Menú de opciones desplegable."),
    ComponentInfo("LazyVerticalGrid", "Cuadrícula vertical con desplazamiento."), ComponentInfo("NavigationRail", "Navegación lateral para pantallas anchas."),
    ComponentInfo("OutlinedTextField", "Campo de entrada con borde y etiqueta."), ComponentInfo("Pager", "Permite deslizar entre páginas."),
    ComponentInfo("Snackbar", "Notificación breve y temporal."), ComponentInfo("TabRow", "Pestañas que alternan contenido."),
    ComponentInfo("Tooltip", "Ayuda contextual asociada a un elemento.")
)

@Composable
private fun ExplorerPage(modifier: Modifier = Modifier) {
    LazyColumn(modifier.fillMaxSize(), contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 100.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item { SectionIntro("Explora Compose", "Contenedores, controles y patrones para construir interfaces por piezas.") }
        item { ContainerGallery() }
        item { ControlGallery() }
        item { MoreControlsGallery() }
    }
}

@Composable
private fun SectionIntro(title: String, subtitle: String) {
    Column(Modifier.padding(vertical = 8.dp)) {
        Text(title, style = MaterialTheme.typography.headlineMedium, color = Ink, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(5.dp)); Text(subtitle, color = Color(0xFF667085))
    }
}

@Composable
private fun DemoSection(title: String, items: List<ComponentInfo>, demo: @Composable () -> Unit) {
    Card(shape = RoundedCornerShape(24.dp), colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White)) {
        Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge, color = Ink, fontWeight = FontWeight.Bold)
            demo()
            HorizontalDivider(color = Color(0xFFE7EAF2))
            items.forEach { info ->
                Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Blue, modifier = Modifier.size(18.dp).padding(top = 2.dp))
                    Column {
                        Text(info.name, fontWeight = FontWeight.SemiBold, color = Ink)
                        Text(info.description, style = MaterialTheme.typography.bodySmall, color = Color(0xFF667085))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ContainerGallery() {
    DemoSection("Contenedores", containerItems) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            items(6) { index -> AssistChip(onClick = {}, label = { Text("Elemento ${index + 1}") }) }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(3) { index -> Surface(color = listOf(Color(0xFFE7ECFF), Color(0xFFE4FAF3), Color(0xFFFFF2D9))[index], shape = RoundedCornerShape(12.dp)) { Text("Grid ${index + 1}", Modifier.padding(10.dp), color = Ink) } }
        }
        FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("FlowRow", "Chip", "Compose", "Layout").forEach { AssistChip(onClick = {}, label = { Text(it) }) }
        }
        FlowColumn(maxItemsInEachColumn = 2, verticalArrangement = Arrangement.spacedBy(4.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            listOf("FlowColumn A", "FlowColumn B", "FlowColumn C", "FlowColumn D").forEach { Text(it, color = Blue) }
        }
        BackdropPatternDemo()
        ConstraintPatternDemo()
    }
}

@Composable
private fun BackdropPatternDemo() {
    Box(Modifier.fillMaxWidth().height(86.dp)) {
        Surface(color = Ink, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth().height(64.dp)) { Text("Fondo · menú y filtros", Modifier.padding(12.dp), color = Color.White) }
        Surface(color = Color.White, shape = RoundedCornerShape(14.dp), shadowElevation = 3.dp, modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(.86f).height(46.dp)) { Text("Panel frontal · contenido", Modifier.padding(12.dp), color = Ink) }
    }
}

@Composable
private fun ConstraintPatternDemo() {
    ConstraintLayout(Modifier.fillMaxWidth().height(76.dp).background(Color(0xFFF3F5FA), RoundedCornerShape(14.dp)).padding(10.dp)) {
        val (title, detail) = createRefs()
        Text("A · título", Modifier.constrainAs(title) { start.linkTo(parent.start); top.linkTo(parent.top) }, color = Ink)
        Text("B · anclado abajo", Modifier.constrainAs(detail) { end.linkTo(parent.end); bottom.linkTo(parent.bottom) }, color = Blue, fontWeight = FontWeight.Bold)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ControlGallery() {
    var checked by remember { mutableStateOf(true) }
    var enabled by remember { mutableStateOf(false) }
    var selected by remember { mutableIntStateOf(0) }
    var slider by remember { mutableStateOf(.42f) }
    var showAlert by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    DemoSection("Controles", controlItems) {
        Row(verticalAlignment = Alignment.CenterVertically) { Checkbox(checked, { checked = it }); Text("Recibir novedades") }
        Row(verticalAlignment = Alignment.CenterVertically) { Switch(enabled, { enabled = it }); Spacer(Modifier.width(8.dp)); Text("Modo interactivo") }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected == 0, { selected = 0 }); Text("Básico")
            Spacer(Modifier.width(10.dp)); RadioButton(selected == 1, { selected = 1 }); Text("Avanzado")
        }
        Slider(value = slider, onValueChange = { slider = it })
        Text("Progreso ${(slider * 100).toInt()}%", color = Ink)
        LinearProgressIndicator(progress = { slider }, modifier = Modifier.fillMaxWidth())
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
            CircularProgressIndicator(modifier = Modifier.size(28.dp), strokeWidth = 3.dp)
            Icon(Icons.Default.Favorite, contentDescription = "Favorito", tint = Color(0xFFDB4772))
            Text("Imagen / Image")
                    CourseImage(Modifier.size(64.dp).clip(RoundedCornerShape(10.dp)))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { showAlert = true }) { Text("AlertDialog") }
            Box {
                TextButton(onClick = { showMenu = true }) { Text("DropdownMenu") }
                DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                    listOf("Perfil", "Ajustes", "Ayuda").forEach { DropdownMenuItem(text = { Text(it) }, onClick = { showMenu = false }) }
                }
            }
        }
        TooltipBox(positionProvider = TooltipDefaults.rememberTooltipPositionProvider(), tooltip = { Surface(color = Ink, shape = RoundedCornerShape(10.dp)) { Text("Consejo contextual", Modifier.padding(horizontal = 12.dp, vertical = 8.dp), color = Color.White) } }, state = rememberTooltipState()) {
            AssistChip(onClick = {}, label = { Text("Mantén pulsado para ver ayuda") }, leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) })
        }
        if (showAlert) AlertDialog(onDismissRequest = { showAlert = false }, icon = { Icon(Icons.Default.Info, contentDescription = null) }, title = { Text("¡Muy bien!") }, text = { Text("Acabas de probar un diálogo de Compose.") }, confirmButton = { TextButton(onClick = { showAlert = false }) { Text("Entendido") } })
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun MoreControlsGallery() {
    var tab by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val pager = rememberPagerState(pageCount = { 3 })
    DemoSection("Más controles y navegación", moreItems) {
        PrimaryTabRow(selectedTabIndex = tab) { listOf("Vista", "Código", "Preview").forEachIndexed { i, title -> Tab(selected = tab == i, onClick = { tab = i }, text = { Text(title) }) } }
        Text("Pestaña seleccionada: ${listOf("Vista", "Código", "Preview")[tab]}", color = Ink)
        Row(verticalAlignment = Alignment.CenterVertically) {
            NavigationRail(modifier = Modifier.height(142.dp)) {
                listOf(Icons.Default.Home, Icons.Default.Favorite, Icons.Default.Info).forEachIndexed { index, icon -> NavigationRailItem(selected = index == tab, onClick = { tab = index }, icon = { Icon(icon, contentDescription = null) }, label = { Text(listOf("Inicio", "Guardado", "Info")[index]) }, alwaysShowLabel = true) }
            }
            Spacer(Modifier.width(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                OutlinedTextField(value = "Compose", onValueChange = {}, label = { Text("OutlinedTextField") }, singleLine = true)
                TextButton(onClick = { showDialog = true }) { Text("Abrir Dialog") }
                TextButton(onClick = { scope.launch { snackbar.showSnackbar("Mensaje temporal: ¡listo!") } }) { Text("Mostrar Snackbar") }
                SnackbarHost(snackbar)
            }
        }
        HorizontalPager(state = pager, modifier = Modifier.fillMaxWidth().height(64.dp)) { page ->
            Surface(color = Color(0xFFE7ECFF), shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxSize()) { Box(contentAlignment = Alignment.Center) { Text("Pager · página ${page + 1}", color = Ink, fontWeight = FontWeight.Bold) } }
        }
        Text("Desliza para cambiar de página · ${pager.currentPage + 1}/3", color = Color(0xFF667085), style = MaterialTheme.typography.bodySmall)
        if (showDialog) Dialog(onDismissRequest = { showDialog = false }, properties = DialogProperties(usePlatformDefaultWidth = true)) {
            Card(shape = RoundedCornerShape(22.dp)) { Column(Modifier.padding(22.dp)) { Text("Dialog", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold); Spacer(Modifier.height(8.dp)); Text("Una ventana modal simple."); TextButton(onClick = { showDialog = false }, modifier = Modifier.align(Alignment.End)) { Text("Cerrar") } } }
        }
    }
}

@Composable
private fun ExercisePage(modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(false) }
    var selected by remember { mutableIntStateOf(0) }
    LazyColumn(modifier.fillMaxSize(), contentPadding = androidx.compose.foundation.layout.PaddingValues(18.dp, 14.dp, 18.dp, 100.dp), verticalArrangement = Arrangement.spacedBy(15.dp)) {
        item { SectionIntro("Reto · Mi espacio", "Una pantalla de práctica con componentes combinados y diseño adaptable.") }
        item {
            Card(shape = RoundedCornerShape(28.dp), colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(13.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Surface(shape = CircleShape, color = Color(0xFFE7ECFF), modifier = Modifier.size(50.dp)) { Box(contentAlignment = Alignment.Center) { Text("FH", color = Blue, fontWeight = FontWeight.ExtraBold) } }
                        Column { Text("Hola, Fabricio", style = MaterialTheme.typography.titleLarge, color = Ink, fontWeight = FontWeight.Bold); Text("Tu panel de aprendizaje", color = Color(0xFF667085)) }
                    }
                    CourseImage(Modifier.fillMaxWidth().height(150.dp).clip(RoundedCornerShape(20.dp)))
                    Text("Aprender haciendo", style = MaterialTheme.typography.headlineSmall, color = Ink, fontWeight = FontWeight.Bold)
                    Text("Cada interfaz se construye con funciones pequeñas y reutilizables. Explora, combina y prueba los componentes.", color = Color(0xFF667085))
                    Text("Tu progreso", fontWeight = FontWeight.SemiBold, color = Ink)
                    LinearProgressIndicator(progress = { .68f }, modifier = Modifier.fillMaxWidth())
                    Text("68% completado · 4 de 6 módulos", color = Blue, style = MaterialTheme.typography.bodySmall)
                    HorizontalDivider()
                    Text("Elige tu siguiente tema", fontWeight = FontWeight.SemiBold, color = Ink)
                    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxWidth().height(155.dp), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(listOf("Layouts", "Controles", "Estado", "Navegación")) { label ->
                            Surface(color = Color(0xFFF3F5FA), shape = RoundedCornerShape(14.dp), onClick = { selected++ }) { Text(label, Modifier.padding(12.dp), color = Ink, fontWeight = FontWeight.Medium) }
                        }
                    }
                    Text("Tema elegido: ${listOf("Layouts", "Controles", "Estado", "Navegación")[selected % 4]}", color = Blue, style = MaterialTheme.typography.bodySmall)
                    Row(verticalAlignment = Alignment.CenterVertically) { Checkbox(checked, { checked = it }); Text("Marcar reto como terminado", color = Ink) }
                    Button(onClick = { selected++ }, modifier = Modifier.fillMaxWidth()) { Text("Continuar aprendiendo") }
                }
            }
        }
        item { Text("Nota: la consigna incluye una captura de referencia para el reto; se reprodujo el estilo de panel formativo con elementos Compose descritos en el laboratorio.", style = MaterialTheme.typography.bodySmall, color = Color(0xFF667085)) }
    }
}

@Preview(showBackground = true)
@Composable
private fun Lab03Preview() { Lab03hanccoTheme(dynamicColor = false) { WelcomePage() } }
