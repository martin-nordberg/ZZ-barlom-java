

export enum ActionType {
    ChangeBrowserRoute,
    Other
}


export interface FluxStandardAction {
    type: ActionType;
    payload: any;
    error?: any;
    meta?: any;
}


export class ChangeBrowserRouteAction
  implements FluxStandardAction {

    readonly type = ActionType.ChangeBrowserRoute;

    readonly payload : { browserRoute : string };

    constructor( browserRoute : string ) {
        this.payload = { browserRoute };
    }

    get browserRoute() {
        return this.payload.browserRoute;
    }

}



export type OtherAction = { type: '' };
export const OtherAction : OtherAction = { type: '' };


export type AnyAction = ChangeBrowserRouteAction
    | OtherAction;