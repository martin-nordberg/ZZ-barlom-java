
import {ChangeBrowserRouteAction} from "./actiontypes";


export function changeBrowserRoute( browserRoute:string ) : ChangeBrowserRouteAction {
    return new ChangeBrowserRouteAction( browserRoute );
}



