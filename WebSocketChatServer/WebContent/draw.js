/**
 * 
 */

function clearMessage() {
	currentUser = null;
	currentGroup = null;
	var e = document.getElementById("msg_history");
	// e.firstElementChild can be used.
	var child = e.lastElementChild;
	while (child) {
		e.removeChild(child);
		child = e.lastElementChild;
	}
}
function addHeaderTable(table) {
	console.log(table);
	console.log('NMQ3');
	var tr = document.createElement("tr");
	tr.classList.add('header');

	var th1 = document.createElement("th");
	th1.style.width = '40%';
	var node1 = document.createTextNode("Frist Name");
	th1.appendChild(node1);

	var th2 = document.createElement("th");
	th2.style.width = '40%';
	var node2 = document.createTextNode("Last Name");
	th2.appendChild(node2);

	var th3 = document.createElement("th");
	th3.style.width = '20%';
	var input = document.createElement("input");
	input.type = 'checkbox';
	input.id = 'check-all';
	input.onclick = function() {
		if (input.checked) {
			$('.check').each(function() {
				this.checked = true;
			});
		} else {
			$('.check').each(function() {
				this.checked = false;
			});
		}
	};
	input.classList.add('check');
	th3.appendChild(input);

	tr.appendChild(th1);
	tr.appendChild(th2);
	tr.appendChild(th3);

	table.appendChild(tr);
}
function clearTableSearch(e) {
	console.log('NMQ2');
	var child = e.lastElementChild;
	while (child) {
		e.removeChild(child);
		child = e.lastElementChild;
	}
}
function showButtonModifyGroup() {
	document.getElementById('myBtn').style.visibility = 'visible';
	document.getElementById('myBtnDelete').style.visibility = 'visible';
}
function hideButtonModifyGroup() {
	document.getElementById('myBtn').style.visibility = 'hidden';
	document.getElementById('myBtnDelete').style.visibility = 'hidden';
}
function showGroup(group) {
	var element = document.getElementById("inbox_chat");

	var fristDiv = document.createElement("div");
	fristDiv.classList.add('chat_list');
	fristDiv.id = "group: " + group.id;
	fristDiv.onclick = function() {
		clearMessage();
		currentGroup = group.id;
		getMessageGroup();
		showButtonModifyGroup();
	};
	var secondDiv = addNewDivTo(fristDiv, 'chat_people');
	var thirdDiv = addNewDivTo(secondDiv, 'chat_img');
	var img = addNewImgTo(thirdDiv, "user-profile.png", "sunil");
	var fourDiv = addNewDivTo(secondDiv, 'chat_ib');

	var h = document.createElement("h5");
	fourDiv.appendChild(h);
	var node = document.createTextNode(group.content);
	h.appendChild(node);

	var s = document.createElement("span");
	s.classList.add('chat_date');

	var node2 = document.createTextNode("Dec 25");
	s.appendChild(node2);
	h.appendChild(s);

	var p = document.createElement("p");

	var node3 = document
			.createTextNode("Test, which is a new approach to have all solutions astrology under one roof.");
	p.appendChild(node3);
	fourDiv.appendChild(p);
	element.appendChild(fristDiv);
}
function showDataTable(myTable, user) {

	var tr = document.createElement("tr");

	var td1 = document.createElement("td");
	var node1 = document.createTextNode(user.fristname);
	td1.appendChild(node1);

	var td2 = document.createElement("td");
	var node2 = document.createTextNode(user.lastname);
	td2.appendChild(node2);

	var td3 = document.createElement("td");
	var checkbox = document.createElement("input");
	checkbox.type = 'checkbox';
	checkbox.id = user.id;
	checkbox.classList.add('check');
	td3.appendChild(checkbox);
	tr.appendChild(td1);
	tr.appendChild(td2);
	tr.appendChild(td3);
	myTable.appendChild(tr);
}
function showFriendList(friend) {

	var element = document.getElementById("inbox_chat");

	var fristDiv = document.createElement("div");
	fristDiv.classList.add('chat_list');
	fristDiv.id = "friend: " + friend.id;
	fristDiv.onclick = function() {
		clearMessage();
		currentUser = friend.id;
		getMessage();
		hideButtonModifyGroup();
	};
	var secondDiv = document.createElement("div");
	secondDiv.classList.add('chat_people');
	fristDiv.appendChild(secondDiv);

	var thirdDiv = document.createElement("div");
	thirdDiv.classList.add('chat_img');
	secondDiv.appendChild(thirdDiv);

	var img = document.createElement("img");
	img.src = "user-profile.png";
	img.alt = "sunil";
	thirdDiv.appendChild(img);

	var fourDiv = document.createElement("div");
	fourDiv.classList.add('chat_ib');
	secondDiv.appendChild(fourDiv);

	var h = document.createElement("h5");
	fourDiv.appendChild(h);
	var node = document
			.createTextNode(friend.fristname + " " + friend.lastname);
	h.appendChild(node);

	var s = document.createElement("span");
	s.classList.add('chat_date');

	var node2 = document.createTextNode("Dec 25");
	s.appendChild(node2);
	h.appendChild(s);

	var p = document.createElement("p");

	var node3 = document
			.createTextNode("Test, which is a new approach to have all solutions astrology under one roof.");
	p.appendChild(node3);
	fourDiv.appendChild(p);
	element.appendChild(fristDiv);
};

function showMyMessage(message) {
	var element = document.getElementById("msg_history");
	var fristDiv = document.createElement("div");
	fristDiv.classList.add('outgoing_msg');
	var secondDiv = document.createElement("div");
	secondDiv.classList.add('sent_msg');
	fristDiv.appendChild(secondDiv);

	var para = document.createElement("p");
	secondDiv.appendChild(para);

	var node = document.createTextNode(message.content);
	para.appendChild(node);

	var s = document.createElement("span");
	s.classList.add('time_date');
	secondDiv.appendChild(s);
	var node2 = document.createTextNode("11:01 AM | June 9");
	s.appendChild(node2);
	fristDiv.addEventListener('contextmenu', function(ev) {
		ev.preventDefault();
		noteMessage(message);
		return false;
	}, false);
	element.appendChild(fristDiv);
};

function showFriendMessage(message) {
	var element = document.getElementById("msg_history");

	var fristDiv = document.createElement("div");
	fristDiv.classList.add('incoming_msg');

	var secondDiv = addNewDivTo(fristDiv, 'incoming_msg_img');

	var image = document.createElement("img");
	image.src = "user-profile.png";
	image.alt = "sunil";
	secondDiv.appendChild(image);

	var thirdDiv = addNewDivTo(fristDiv, 'received_msg')

	var fourdDiv = addNewDivTo(thirdDiv, 'received_withd_msg');

	var para = document.createElement("p");
	fourdDiv.appendChild(para);
	var node = document.createTextNode(message.content);
	para.appendChild(node);

	var s = document.createElement("span");
	s.classList.add('time_date');

	var node2 = document.createTextNode("11:01 AM | June 9");
	s.appendChild(node2);
	fourdDiv.appendChild(s);

	fristDiv.addEventListener('contextmenu', function(ev) {
		ev.preventDefault();
		noteMessage(message);
		return false;
	}, false);

	element.appendChild(fristDiv);
};

function addNewDivTo(parent, style) {
	var div = document.createElement("div");
	div.classList.add(style);
	parent.appendChild(div);
	return div;
}
function addNewImgTo(parent, src, alt) {
	var img = document.createElement("img");
	img.src = src;
	img.alt = alt;
	parent.appendChild(img);
	return img;
}

