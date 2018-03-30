/**
 * http://usejsdoc.org/
 */
var filesystem = null;
$('#save').click(function(e) {
	initFileSystem();
	console.log('init file system');
	//saveFile('index.json', 'content');
	while (filesystem != null) {
		console.log(filesystem);
	}

})

// Request a FileSystem and set the filesystem variable.
function initFileSystem() {
	console.log('file system is requested');
	window.requestFileSystem = window.requestFileSystem || window.webkitRequestFileSystem;
	navigator.webkitPersistentStorage.requestQuota(1024 * 1024 * 100,
		function(grantedSize) {

			// Request a file system with the new size.    	
			console.log(grantedSize);
			window.webkitRequestFileSystem(window.PERSISTENT, grantedSize, function(fs) {

				// Set the filesystem variable.
				filesystem = fs;
				console.log('file system : ' + filesystem);
				// Setup event listeners on the form.
				//setupFormEventListener();
				console.log('request is granted');
				// Update the file browser.
				saveFile('index.json', 'content');

			}, errorHandler);

		}, errorHandler);
}

function saveFile(filename, content) {
	filesystem.root.getFile(filename, {
		create : true
	}, function(fileEntry) {

		fileEntry.createWriter(function(fileWriter) {

			fileWriter.onwriteend = function(e) {
				// Update the file browser.
				//listFiles();

				/* // Clean out the form field.
				filenameInput.value = '';
				contentTextArea.value = '';
 */
				// Show a saved message.
				//		          messageBox.innerHTML = 'File saved!';
			};

			fileWriter.onerror = function(e) {
				console.log('Write error: ' + e.toString());
				alert('An error occurred and your file could not be saved!');
			};

			var contentBlob = new Blob([ content ], {
				type : 'text/plain'
			});

			fileWriter.write(contentBlob);
			console.log(fileWriter.toString);

		}, errorHandler);

	}, errorHandler);
}

// A simple error handler to be used throughout this demo.
function errorHandler(error) {
	var message = '';

	console.log(error.message);
	switch (error.name) {
	case 'SecurityError':
		message = 'Security Error';
		break;
	default:
		message = 'Unknown Error';
		break;
	}
	console.log(message);
}

$('#filepicker').change(function(e) {

	var ua = window.navigator.userAgent;
	var msie = ua.indexOf("MSIE ");
	let target = e.target;
	try {
		var fileSize = 0;
		//for IE
		if (msie > 0) {
			//before making an object of ActiveXObject, 
			//please make sure ActiveX is enabled in your IE browser
			var objFSO = new ActiveXObject("Scripting.FileSystemObject");
			var filePath = $("#" + fileid)[0].value;
			var objFile = objFSO.getFile(filePath);
			var fileSize = objFile.size; //size in kb
			fileSize = fileSize / 1048576; //size in mb 
		}
		//for FF, Safari, Opera and Others
		else {
			fileSize = $("#filepicker")[0].files[0].size //size in kb
			console.log(fileSize);
			fileSize = fileSize / 1048576; //size in mb 
		}
		alert("Uploaded File Size is " + fileSize + "MB");
	} catch (e) {
		alert("Error is :" + e);
	}
	let table = document.createElement('table');
	table.setAttribute("border", 1);
	let headerrow = document.createElement('tr');
	headerrow.setAttribute('bgcolor', '#FF0000');
	let fileheader = document.createElement('td');
	var filenamestr = 'File Name';
	fileheader.innerHTML = filenamestr.bold();
	headerrow.appendChild(fileheader);
	let sizeheader = document.createElement('td');
	var sizestr = 'Size';
	sizeheader.innerHTML = sizestr.bold();
	headerrow.appendChild(sizeheader);
	let typeheader = document.createElement('td');
	var typestr = 'Type';
	typeheader.innerHTML = typestr.bold();
	headerrow.appendChild(typeheader);
	let lastModheader = document.createElement('td');
	var lastModStr = 'Last Modified';
	lastModheader.innerHTML = lastModStr.bold();
	headerrow.appendChild(lastModheader);
	let lastModDateheader = document.createElement('td');
	var lastModDateStr = 'Last Modified Date';
	lastModDateheader.innerHTML = lastModDateStr.bold();
	headerrow.appendChild(lastModDateheader);
	let pathheader = document.createElement('td');
	var pathheaderStr = 'Relative Path';
	pathheader.innerHTML = pathheaderStr.bold();
	headerrow.appendChild(pathheader);
	table.appendChild(headerrow);
	let output = document.getElementById("listing");
	var foldName = "Folder Name";
	output.innerHTML = "";
	var totalSize = [].reduce.call(this.files, function(tot, currFile) {
		var fileRelativePath = currFile.webkitRelativePath;
		if (fileRelativePath.indexOf('/') > 0) {
			var foldersArray = fileRelativePath.split("/");
			var arrlength = foldersArray.length;
			//console.log('length of array : '+arrlength);								
			foldName = foldersArray.splice(0, 1);

			countofSlashes = arrlength - 1;
			foldersArray.splice(countofSlashes - 1, 1);
			console.log('count of slash : ' + countofSlashes);
			if (countofSlashes > 0) {
				foldersArray.forEach(function(folder) {
					console.log(folder);
					let row1 = document.createElement('tr');
					let folderElement = document.createElement('td');
					folderElement.innerHTML = folder;
					row1.appendChild(folderElement);

					let sizeElement = document.createElement('td');
					sizeElement.innerHTML = '4096';
					row1.appendChild(sizeElement);

					let typeElement = document.createElement('td');
					typeElement.innerHTML = 'Folder';
					row1.appendChild(typeElement);

					let lastModElement = document.createElement('td');
					lastModElement.innerHTML = currFile.lastModified;
					row1.appendChild(lastModElement);

					let lastModDateElement = document.createElement('td');
					lastModDateElement.innerHTML = currFile.lastModifiedDate;
					row1.appendChild(lastModDateElement);

					let relPathElement = document.createElement('td');
					relPathElement.innerHTML = currFile.webkitRelativePath;
					row1.appendChild(relPathElement);

					table.appendChild(row1);
				});
			}
		}
		document.getElementById("folderName").innerHTML = foldName;
		//document.getElementByTagName("h2").innerHTML="Folder Name : "+foldName;
		//console.log(currFile.name , ' size=', currFile.size);
		let row = document.createElement('tr');
		let namecolumn = document.createElement('td');
		namecolumn.innerHTML = currFile.name;
		row.appendChild(namecolumn);
		let sizecolumn = document.createElement('td');
		sizecolumn.innerHTML = currFile.size;
		row.appendChild(sizecolumn);
		let typecolumn = document.createElement('td');
		typecolumn.innerHTML = currFile.type;
		row.appendChild(typecolumn);
		let lastModColumn = document.createElement('td');
		lastModColumn.innerHTML = currFile.lastModified;
		row.appendChild(lastModColumn);
		let lastModDateColumn = document.createElement('td');
		lastModDateColumn.innerHTML = currFile.lastModifiedDate;
		row.appendChild(lastModDateColumn);
		let relPathColumn = document.createElement('td');
		relPathColumn.innerHTML = currFile.webkitRelativePath;
		row.appendChild(relPathColumn);
		table.appendChild(row);

		return tot + currFile.size;
	}, 0);
	output.appendChild(table);
	console.log('Total size = ', totalSize);
});