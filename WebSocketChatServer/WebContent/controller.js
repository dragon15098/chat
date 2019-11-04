function createConnect() {
	console.log(websocket);
	if (typeof websocket != 'undefined'
			&& websocket.readyState == WebSocket.OPEN) {
		console.log(websocket);
		var message = $('#textMessage').val();
		var context = $('#textMessage').val();
		var message = {
			'token' : cookie,
			'function' : 'START_CONNECTION'
		}
		console.log(message);
		websocket.send(JSON.stringify(message));
	}
}
function addFriendToGroup() {
	$('.check').each(function() {
		if (this.id != 'check-all') {
			listIdAdd.push(this.id);
		}
	});
	var message = {
		'token' : cookie,
		'function' : 'ADD_FRIENDS_TO_GROUP',
		'friendIds' : listIdAdd,
		'toGroupUser' : currentGroup
	}
	console.log(message);
	websocket.send(JSON.stringify(message));
}
function searchFriend() {
	var myTable = document.getElementById('myTable');
	clearTableSearch(myTable);
	addHeaderTable('myTable');
	var text = $('#textSearch').val();
	if (typeof websocket != 'undefined'
			&& websocket.readyState == WebSocket.OPEN) {
		console.log(websocket);
		var textSearch = $('#textSearch').val();
		console.log(textSearch);
		var searchRequest = {
			'toGroupUser' : currentGroup,
			'token' : cookie,
			'textSearch' : textSearch,
			'function' : 'SEARCH_FRIEND'
		}
		console.log(searchRequest);
		websocket.send(JSON.stringify(searchRequest));
	}
}
function deleteFriend() {
	$('.check').each(function() {
		if (this.id != 'check-all') {
			listIdAdd.push(this.id);
		}
	});
	var message = {
		'token' : cookie,
		'function' : 'DELETE_FRIENDS_FROM_GROUP',
		'friendIds' : listIdAdd,
		'toGroupUser' : currentGroup
	}
	console.log(message);
	websocket.send(JSON.stringify(message));
}
function searchFriendInGroup() {
	var myTable = document.getElementById('myTableDelete');
	clearTableSearch(myTable);
	clearTableSearch('myTableDelete');
	addHeaderTable('myTableDelete');
	if (typeof websocket != 'undefined'
			&& websocket.readyState == WebSocket.OPEN) {
		var message = {
			'toGroupUser' : currentGroup,
			'token' : cookie,
			'function' : 'GET_FRIEND_IN_GROUP'
		}
		console.log(message);
		websocket.send(JSON.stringify(message));

	}
}
function showResultSearch(table, data) {
	for (i in data) {
		console.log(data);
		showDataTable(table, data[i]);
	}
}
function getMessage() {
	if (typeof websocket != 'undefined'
			&& websocket.readyState == WebSocket.OPEN) {
		var message = {
			'toUser' : currentUser,
			'token' : cookie,
			'function' : 'GET_MESSAGE'
		}
		console.log(message);
		websocket.send(JSON.stringify(message));

	}
}
function getMessageGroup() {
	if (typeof websocket != 'undefined'
			&& websocket.readyState == WebSocket.OPEN) {
		var message = {
			'toGroupUser' : currentGroup,
			'token' : cookie,
			'function' : 'GET_MESSAGE_GROUP'
		}
		console.log(message);
		websocket.send(JSON.stringify(message));
	}
}
function sendMessage() {
	if (typeof websocket != 'undefined'
			&& websocket.readyState == WebSocket.OPEN) {
		if (currentUser != null) {
			var content = $('#textMessage').val();
			$('#textMessage').val("");
			var message = {
				'toUser' : currentUser,
				'token' : cookie,
				'content' : content,
				'function' : 'SEND_MESSAGE_TO_USER'
			}
			console.log(message);
			websocket.send(JSON.stringify(message));
			showMyMessage(content);
		} else {
			var content = $('#textMessage').val();
			$('#textMessage').val("");
			var message = {
				'toGroupUser' : currentGroup,
				'token' : cookie,
				'content' : content,
				'function' : 'SEND_MESSAGE_TO_GROUP'
			}
			console.log(message);
			websocket.send(JSON.stringify(message));
			showMyMessage(content);
		}
	}
}
