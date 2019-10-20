/**
 * 
 */

function clearMessage(){
	currentUser = null;
	currentGroup = null;
	  var e = document.getElementById("msg_history"); 
	 //e.firstElementChild can be used. 
    var child = e.lastElementChild;  
    while (child) { 
        e.removeChild(child); 
        child = e.lastElementChild; 
    } 
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
	};
	var secondDiv = addNewDivTo(fristDiv, 'chat_people');
	var thirdDiv = addNewDivTo(secondDiv, 'chat_img');
	var img = addNewImgTo(thirdDiv, "user-profile.png", "sunil");
	var fourDiv = addNewDivTo(secondDiv, 'chat_ib');

	var h = document.createElement("h5");
	fourDiv.appendChild(h);
	var node = document
			.createTextNode(group.content);
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
function showFriendList(friend) {
	console.log(friend);
	var element = document.getElementById("inbox_chat");

	var fristDiv = document.createElement("div");
	fristDiv.classList.add('chat_list');
	fristDiv.id = "friend: " + friend.id;
	fristDiv.onclick = function() {
		clearMessage();
		currentUser = friend.id;
		getMessage();
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
	var node = document.createTextNode(message);
	para.appendChild(node);

	var s = document.createElement("span");
	s.classList.add('time_date');
	secondDiv.appendChild(s);
	var node2 = document.createTextNode("11:01 AM | June 9");
	s.appendChild(node2);
	element.appendChild(fristDiv);
};

function showFriendMessage(message) {
	var element = document.getElementById("msg_history");

	var fristDiv = document.createElement("div");
	fristDiv.classList.add('incoming_msg');

	var secondDiv = document.createElement("div");
	secondDiv.classList.add('incoming_msg_img');

	var image = document.createElement("img");
	image.src = "https://ptetutorials.com/images/user-profile.png";
	image.alt = "sunil";
	secondDiv.appendChild(image);
	fristDiv.appendChild(secondDiv);

	var thirdDiv = document.createElement("div");
	thirdDiv.classList.add('received_msg');
	fristDiv.appendChild(thirdDiv);

	var fourdDiv = document.createElement("div");
	fourdDiv.classList.add('received_withd_msg');
	thirdDiv.appendChild(fourdDiv);

	var para = document.createElement("p");
	fourdDiv.appendChild(para);
	var node = document.createTextNode(message);
	para.appendChild(node);

	var s = document.createElement("span");
	s.classList.add('time_date');

	var node2 = document.createTextNode("11:01 AM | June 9");
	s.appendChild(node2);
	fourdDiv.appendChild(s);

	element.appendChild(fristDiv);
};

function addNewDivTo(parent, style){
	var div = document.createElement("div");
	div.classList.add(style);
	parent.appendChild(div);
	return div;
}
function addNewImgTo(parent, src, alt){
	var img = document.createElement("img");
	img.src = src;
	img.alt = alt;
	parent.appendChild(img);
	return img;
}

