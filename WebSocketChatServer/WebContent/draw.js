/**
 * 
 */

function clearMessage(){
	  var e = document.getElementById("msg_history"); 
	 //e.firstElementChild can be used. 
    var child = e.lastElementChild;  
    while (child) { 
        e.removeChild(child); 
        child = e.lastElementChild; 
    } 
}

function showFriendList(friend) {
	var element = document.getElementById("inbox_chat");

	var fristDiv = document.createElement("div");
	fristDiv.classList.add('chat_list');
	fristDiv.id = "" + friend.id;
	fristDiv.onclick = function() {
		clearMessage();
		currentUser = fristDiv.id;
		getMessage();
	};

	var secondDiv = document.createElement("div");
	secondDiv.classList.add('chat_people');
	fristDiv.appendChild(secondDiv);

	var thirdDiv = document.createElement("div");
	thirdDiv.classList.add('chat_img');
	secondDiv.appendChild(thirdDiv);

	var img = document.createElement("img");
	img.src = "https://ptetutorials.com/images/user-profile.png";
	img.alt = "sunil";
	thirdDiv.appendChild(img);

	var fourDiv = document.createElement("div");
	fourDiv.classList.add('chat_ib');
	secondDiv.appendChild(fourDiv);

	var h = document.createElement("h5");
	fourDiv.appendChild(h);
	var node = document
			.createTextNode(friend.firstName + " " + friend.lastName);
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
	console.log(message);
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
