
interface Notifier {
    notify( changeRecord : any ) : void;
}

export class Example {

    constructor() {
        this._modifier = "cruel";
        this._name = "world";
        this._notifier = Object['getNotifier']( this );
    }

    private _modifier : string;

    public get modifier() {
        return this._modifier;
    }

    public set modifier( value : string ) {
        this._notifier.notify(
            {
                type: 'change.modifier',
                name: 'modifier',
                oldValue: this._modifier,
                newValue: value
            }
        );
        this._modifier = value;
        console.log( value );
    }

    private _name : string;

    public get name() {
        return this._name;
    }

    public set name( value : string ) {
        this._notifier.notify(
            {
                type: 'change.name',
                name: 'name',
                oldValue: this._name,
                newValue: value
            }
        );
        this._name = value;
        console.log( value );
    }

    private _notifier : Notifier;

}

export class ExampleVM {

    constructor( model : Example ) {

        var me = this;

        me.modifier = model.modifier;
        me.name = model.name;

        Object['observe'](
            model, function ( changes ) {
                changes.forEach(
                    function ( change ) {
                        console.log( change );
                        me[change.name] = change.newValue
                    }
                )
            }, ['change.modifier','change.name']
        );
    }

    public modifier : string;

    public name : string;

}