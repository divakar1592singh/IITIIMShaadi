// See relevant post at https://codepen.io/Javarome/post/horizontal-page-slider
var root = document.querySelector('.tree-slider');
var radios = root.querySelectorAll('input[type="radio"]');
var slider = root.getElementsByClassName('slider')[0];
var slide = slider.getElementsByClassName('slide')[0];
var touchStartX, touchStartY, tx, currentTx;
var currentPos, index;
var finishThresold = 100;

function swipeStart(e) {
  touchStartX = e.touches[0].pageX;
  touchStartY = e.touches[0].pageY;
  currentTx = index * -slide.offsetWidth;
  slider.style.transition = 'none'; // Disable transition during drag
}
function translateTo(tx) {
  slider.style.transform = 'translateX(' + tx + 'px)';
}
function requestTranslateTo(tx) {
  requestAnimationFrame(translateTo.bind(this, tx));
}
function setIndex(newIndex) {
  var indexChanged = newIndex !== index && newIndex >= 0 && newIndex < radios.length;
  if (indexChanged) {
    radios[index].checked = false;
    index = newIndex;
    radios[index].checked = true;
 } else {
    highlight();
    setTimeout(cancelHighlight, 300);
  }
  return indexChanged;
}
function swipeEnd(e) {
  slider.style.transition = 'transform 0.3s ease'; // Use CSS transition to go to selected slide
  var newIndex = index;
  var swipeThresold = slide.offsetWidth / 8;
  if (tx < -swipeThresold) {
    newIndex++;
    currentTx -= slide.offsetWidth;
  } else if (tx > swipeThresold) {
    newIndex--;
    currentTx += slide.offsetWidth;
  }
  if (setIndex(newIndex)) {
    requestTranslateTo(currentTx);
  }
  setTimeout(function() {
    slider.style.transform = '';  // Remove inline style to allow translate using CSS dots
  }, 300);                        // ...at transition's end
}
function cancelHighlight() {
  var done = root.querySelector('.done.' + radios[index].id);
  var highlightPos = done.className.indexOf(' highlight');
  if (highlightPos > 0) {
    done.className = done.className.substring(0, highlightPos);
  }
}
function highlight() {
  root.querySelector('.done.' + radios[index].id).className += ' highlight';
}
function swipeDrag(e) {
 e.preventDefault();
  var touchMoveX = e.touches[0].pageX;
  tx = touchMoveX - touchStartX;
  var touchMoveY = e.touches[0].pageY;
  var ty = touchMoveY - touchStartY;
  var fullTx = currentTx + tx;
  if (fullTx < 0) {
    if (tx < 0 && index >= radios.length - 1) { // End
      highlight();
    } else {
      requestTranslateTo(fullTx);
    }
  } else if (ty < finishThresold) {
    highlight();
  }
  if (ty > finishThresold) {
    finish();
  }
}
function finish() {
  requestAnimationFrame(function() {
    slider.style.boxShadow = '0em 0em 2em #acacac';
    root.style.transform = 'translateY(150vh) rotate(-15deg)';
  }); 
  slider.removeEventListener('touchstart', swipeStart);
  slider.removeEventListener('touchmove', swipeDrag);
  slider.removeEventListener('touchend', swipeEnd);
  document.removeEventListener('keydown', swipeKeyDown);
}
function swipeKeyDown(e) {
  var code = e.keyCode ? e.keyCode : e.which;
  switch (code) {
    case 27:  // Esc
      finish();
      break;
    case 8:
    case 37:
      setIndex(index - 1);
      break;
    default:
      setIndex(index + 1);
      break;
  }
}
function begin() {
  currentPos = 0;
  index = 0;
  radios[index].checked = true;
  requestAnimationFrame(function() {
    slider.style.transition = ''; // Disable at restart         
    slider.style.transform = '';  // Remove inline style to allow
    root.style.transform = '';  // Remove inline style to
    setTimeout(function() {
      slider.style.transition = 'transform 0.3s ease'; // Enable for CSS radio-trigerred transitions
    });
  }); 
  slider.addEventListener('touchstart', swipeStart);
  slider.addEventListener('touchmove', swipeDrag);
  slider.addEventListener('touchend', swipeEnd);
  document.addEventListener('keydown', swipeKeyDown);
}
begin();