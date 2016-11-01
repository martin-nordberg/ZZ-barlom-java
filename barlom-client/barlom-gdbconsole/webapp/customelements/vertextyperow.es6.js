/**
 * Custom element for one vertex type in a list of them.
 */

class VertexTypeRow extends HTMLElement {

  // Fires when an instance of the element is created.
  createdCallback() {
    let me = $( this );

    this.innerHTML =
      `
        <a href="#vertextypes/${me.attr( 'uuid' )}">${me.attr( 'name' )}</a><br>
      `;
  };

}

document.registerElement( 'barlom-gdb-vertextyperow', VertexTypeRow );
