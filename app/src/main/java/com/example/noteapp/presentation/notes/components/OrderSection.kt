package com.example.noteapp.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.noteapp.domain.util.NoteOrder
import com.example.noteapp.domain.util.OrderType

@Composable
fun  OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
){
    Column(
      modifier = modifier
    ){
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = noteOrder is NoteOrder.Title,
                onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
            )
//            Spacer(modifier = modifier.width(4.dp))
            DefaultRadioButton(
                text = "Date",
                selected = noteOrder is NoteOrder.Date,
                onSelect = {  onOrderChange(NoteOrder.Date(noteOrder.orderType))  }
            )
//            Spacer(modifier = modifier.width(4.dp))
            DefaultRadioButton(
                text = "color",
                selected = noteOrder is NoteOrder.Color,
                onSelect = {  onOrderChange(NoteOrder.Color(noteOrder.orderType))  }
            )
        }
//        Spacer(modifier = modifier.height(4.dp))
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = {  onOrderChange(noteOrder.copy(OrderType.Ascending))  }
            )
//            Spacer(modifier = modifier.width(4.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = {  onOrderChange(noteOrder.copy(OrderType.Descending))  }
            )
        }
    }
}