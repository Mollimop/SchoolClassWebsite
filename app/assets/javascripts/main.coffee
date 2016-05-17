ready = ->
  aside = -> $('#aside').css('width', '200px')
  asidere = -> $('#aside').css('width', '1px')
  $('#b').hover(aside)
  $('#aside').hover(aside, asidere)


$(document).ready(ready)
$(document).on('page:change', ready)
