export enum Key {
    BACKSPACE = 8,
    TAB = 9,
    ENTER = 13,
    SHIFT = 16,
    CTRL = 17,
    ALT = 18,
    ESC = 27,
    SPACEBAR = 32,
    ARROW_LEFT = 37,
    ARROW_UP = 38,
    ARROW_RIGHT = 39,
    ARROW_DOWN = 40
}


export function simulateKeyEvent(character: string) {
    const evt = document.createEvent('KeyboardEvent');
    (evt['initKeyEvent'] || evt.initKeyboardEvent)('keypress', true, true, window,
                      0, 0, 0, 0,
                      0, character.charCodeAt(0));
    let canceled = !document.body.dispatchEvent(evt);
    if (canceled) {
      // A handler called preventDefault
      alert('canceled');
    } else {
      // None of the handlers called preventDefault
      alert('not canceled');
    }
  }
