package com.example.noteapp.presentation.notes.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.example.noteapp.domain.model.Note
import com.example.noteapp.ui.theme.NoteAppTheme

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onDeleteClick: () -> Unit
){
    Box(
        modifier = modifier
    ){
        Canvas(modifier = modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(note.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(note.color, 0x000000, 0.2f)
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Column{
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(end = 32.dp)
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.surface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = modifier.height(8.dp))
                Text(
                    text = note.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.surface,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = modifier.height(4.dp))
            IconButton(
                onClick = onDeleteClick,
//            modifier = modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    tint = Color.Black,
                    contentDescription = "Delete note"
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun NoteItemPreview(){
//    NoteAppTheme {
//        NoteItem(
//            note = Note(
//                title = "Wake Up",
//                content = "I need to wake up at 4:00am get to coding, not before having breakfast and praying salah, all this must be done in 40mins this will make me effective",
//                timestamp = "06/11/2023".toLong(),
//                color = Color.Yellow.toArgb(),
//                id = 1
//            ),
//            onDeleteClick = {}
//        )
//    }
//}
