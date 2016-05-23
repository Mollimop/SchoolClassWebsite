ready = ->

  aside = ->
    $('#aside').stop()
    $('#aside').fadeIn(500)
  asidere = ->
    $('#aside').stop()
    $('#aside').fadeOut(500)

  boblue = -> $(this).css('border', '1px solid #0288D1')
  boblack = -> $(this).css('border', '1px solid #212121')
  blight = -> $(this).css('background', '#03A9F4')
  bdark = -> $(this).css('background', '#0288D1')
  vibrate = -> $(this).vibrate("short")

  $('#b').hover(aside)
  $('#aside').hover(null, asidere)

  $('input[type = "submit"]').focus(blight).focusout(bdark)
  $('input[type = "submit"]').click(vibrate)

  $('select').focus(blight).focusout(bdark)
  $('select').click(vibrate)

  $('input.required').focus(boblack).focusout(boblue)

$(document).on('page:change', ready)
