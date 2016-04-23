window.onload = ->
  aside = -> document.getElementById('aside').style.width = "200px"
  asidere = -> document.getElementById('aside').style.width = "1px"

  document.getElementById('b').onmouseover = aside
  document.getElementById('aside').onmouseover = aside
  document.getElementById('content').onmouseover = asidere
