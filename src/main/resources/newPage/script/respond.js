document.onload = r();

function r(){
  var userAgent = navigator.userAgent.toLowerCase();
  var w = window.innerWidth;
  var h = window.innerHeight;
  if(w < 850){
    document.getElementById('background').style.height = (h - 60) + 'px';
  }else{
    document.getElementById('background').style.height = '100%';
  }
}

function navBar(){
  if(document.getElementById('header').style.height == '100%'){
    document.getElementById('links').style.visibility = 'collapse';
    document.getElementById('header').style.overflow = 'hidden';
    document.getElementById('header').style.height = '60px';
    document.getElementById('h').style.fontSize = '350%';
  }else{
    document.getElementById('links').style.visibility = 'visible';
    document.getElementById('header').style.overflow = 'auto';
    document.getElementById('header').style.height = '100%';
    document.getElementById('h').style.fontSize = '0';
    }
}

function linkClick(){
  var w = window.innerWidth;
  var h = window.innerHeight;
  if((w/h < 4/3) || (w < 850)){
    document.getElementById('links').style.visibility = 'collapse';
    document.getElementById('header').style.overflow = 'hidden';
    document.getElementById('header').style.height = '60px';
    document.getElementById('h').style.fontSize = '350%';
  }
}
