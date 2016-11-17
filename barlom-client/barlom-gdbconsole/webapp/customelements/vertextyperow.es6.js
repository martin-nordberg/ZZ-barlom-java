/**
 * Custom element for one vertex type in a list of them.
 */

class VertexTypeRow extends HTMLElement {

  // Fires when an instance of the element is created.
  createdCallback() {
    let me = $( this );

    let deleteId = 'vertextypes-' + me.attr( 'uuid' ) + '-delete';

    this.innerHTML =
      `
        <a href="#vertextypes/${me.attr( 'uuid' )}">${me.attr( 'name' )}</a>
        <a id="${deleteId}" href="#vertextypes">X</a>
        <br>
      `;

    $( `#${deleteId}` ).click( function() {
        $.ajax({
            url: '/barlomgdbconsolecontent/vertextypes/' + me.attr( 'uuid' ),
            type: 'DELETE',
            success: function(result) {
                // Do something with the result
            }
        })  .done(
                    function () {
                        riot.route( '/vertextypes', true );
                    }
            )
            .fail(
                    function ( jqxhr, textStatus, error ) {
                        var err = textStatus + ", " + error;
                        alert( "Request Failed: " + err );
                    }
            );
    } );

  };

}

document.registerElement( 'barlom-gdb-vertextyperow', VertexTypeRow );
