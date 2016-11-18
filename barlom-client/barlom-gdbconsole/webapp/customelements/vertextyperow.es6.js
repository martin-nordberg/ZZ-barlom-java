/**
 * Custom element for one vertex type in a list of them.
 */

class VertexTypeRow extends HTMLElement {

  // Fires when an instance of the element is created.
  createdCallback() {
    let me = $( this );

    let deleteId = 'vertextypes-' + me.attr( 'uuid' ) + '-delete';
    let createId = 'vertextypes-' + me.attr( 'uuid' ) + '-create';

    this.innerHTML =
      `
        <a href="#vertextypes/${me.attr( 'uuid' )}">${me.attr( 'name' )}</a>
        <a id="${deleteId}" href="#vertextypes">X</a>
        <a id="${createId}" href="#vertextypes">+</a>
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

    $( `#${createId}` ).click( function() {
        $.ajax({
            url: '/barlomgdbconsolecontent/vertextypes/' + '6a0e5e00-ad1a-11e6-8cf0-080027832b30',
            type: 'POST',
            contentType: 'application/json',
            data: `{ "uuid": "6a0e5e00-ad1a-11e6-8cf0-080027832b30", "name": "VertexTypeA" }`,
            dataType: 'json',
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
