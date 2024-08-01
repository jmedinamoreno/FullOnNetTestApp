package com.example.fullonnettestapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.fullonnettestapp.data.model.User
import com.example.fullonnettestapp.ui.theme.FullOnNetTestAppTheme
import com.example.fullonnettestapp.viewmodel.UsersViewModel


@Composable
fun UsersScreen(usersViewModel: UsersViewModel, modifier: Modifier = Modifier) {
    val usersVM = usersViewModel.userList.observeAsState(emptyList())
    UserList(users = usersVM.value, modifier = modifier)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserList(users:List<User>, modifier: Modifier = Modifier) {
    var selectedItem by remember { mutableStateOf(-1) }
    if(users.isEmpty()){
        Text(modifier = modifier,text = "Loading...")
    }else {
        LazyColumn(modifier = modifier) {
            items(users) { user ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 2.dp),
                    onClick = { selectedItem = users.indexOf(user) }
                ) {
                    if(selectedItem==users.indexOf(user)) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(vertical = 3.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 24.sp
                                ),
                                text = "${user.firstname} ${user.lastname}")
                            if(user.photo!=null) {
                                GlideImage(
                                    modifier = Modifier.height(64.dp).clip(RoundedCornerShape(size = 16.dp)).aspectRatio(1f),
                                    model = user.photo,
                                    contentDescription = "${user.firstname} ${user.lastname}",
                                    loading = placeholder(ColorPainter(Color.Gray)),
                                    failure = placeholder(ColorPainter(Color.Red))
                                )
                            }else{
                                Text(text = "No photo")
                            }
                        }
                    }else{
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(vertical = 3.dp)
                                .fillMaxWidth(),
                            text = "${user.firstname} ${user.lastname}")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UsersPreview() {
    FullOnNetTestAppTheme {
        UserList(listOf(User(firstname = "jose", lastname = "martinez"), User(firstname = "adrian", lastname = "perez")))
    }
}