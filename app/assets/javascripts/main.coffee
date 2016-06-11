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

  search = ->
    word = document.forms["sform"].elements[0].value
    events = $('.event')
    $('.event').css('display', 'block')
    i = 0
    for i in [0...events.length]
      num = -> return i.toString()
      $('.event:eq(' + num() + ')' ).css('display', 'none') unless $('.event:eq(' + num() + ')' ).text().includes(word)
  reset = -> $('.event').css('display', 'block')


  $('#b').hover(aside)
  $('#aside').hover(null, asidere)

  $('input[type = "submit"]').focus(blight).focusout(bdark)
  $('input[type = "submit"]').click(vibrate)

  $('select').focus(blight).focusout(bdark)
  $('select').click(vibrate)

  $('input.required').focus(boblack).focusout(boblue)

  $('#searchbutton').click(search)
  $('#reset').click(reset)

$(document).on('page:change', ready)
