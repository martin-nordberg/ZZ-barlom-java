// import {IRepository} from "../domain/impl/Repository";
// /**
//  * Actions for changing vertex types.
//  */
//
// export const ACTION_RENAME_VERTEX_TYPE = "barlom/vertextypes/RENAME";
//
//
// class Action_RenameVertexType {
//
//     constructor(
//         readonly vertexTypeUuid : string,
//         readonly newName : string,
//         readonly type = ACTION_RENAME_VERTEX_TYPE
//     ) {}
//
// }
//
// export function renameVertexType( vertexTypeUuid : string, newName : string ) {
//     return new Action_RenameVertexType( vertexTypeUuid, newName );
// }
//
// export function update( repository: IRepository, action : { type : string } ) : IRepository {
//
//     switch ( action.type ) {
//         case ACTION_RENAME_VERTEX_TYPE:
//             const renameAction = action as Action_RenameVertexType;
//             return repository.withVertexType( repository.getVertexTypeByUuid( renameAction.vertexTypeUuid ).withName( renameAction.newName ) );
//         default:
//             return repository;
//     }
//
// }