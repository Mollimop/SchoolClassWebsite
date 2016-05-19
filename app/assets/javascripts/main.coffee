ready = ->
  aside = -> $('#aside').css('width', '200px')
  asidere = -> $('#aside').css('width', '1px')

  blight = -> $(this).css('background', '#03A9F4')
  bdark = -> $(this).css('background', '#0288D1')

  $('#b').hover(aside)
  $('#aside').hover(aside, asidere)
  $('input[type = "submit"]').focus(blight).focusout(bdark)
  $('select').focus(blight).focusout(bdark)

$(document).on('page:change', ready)
