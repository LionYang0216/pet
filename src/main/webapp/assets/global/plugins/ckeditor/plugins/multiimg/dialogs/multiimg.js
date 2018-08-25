CKEDITOR.dialog.add("multiimg",function(a) { 
	var ROOT_PATH = "";    // your root path 
	return {  
		title: "批量上传图片",  
		contents: [{  
			id: "tab1",  
			label: "",  
			title: "",  
			padding: 0,  
			elements: [{  
				type: "html",  
				style: "min-width:660px;min-height:500px;",  
				html: '<iframe id="uploadFrame" src="./image.html?v=' +new Date().getSeconds() + '" frameborder="0"></iframe>'  
			}]  
		}],  
		onOk: function() {
			var ins = a;  
			var num = window.imgs.length;
			var imgHtml = "";
			for(var i=0;i<num;i++){  
				imgHtml += "<p><img src=\"" + window.imgs[i] + "\" /></p>";   
			}
			ins.insertHtml(imgHtml);
			window.imgs =[];
		},  
		onShow: function () {  
		    document.getElementById("uploadFrame").setAttribute("src", "./image.html?v=" + new Date().getSeconds().toString());
		} 
	}  
})  
 