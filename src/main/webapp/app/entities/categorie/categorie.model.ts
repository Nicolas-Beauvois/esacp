import { IRapport } from 'app/entities/rapport/rapport.model';

export interface ICategorie {
  id?: number;
  categorie?: string | null;
  rapports?: IRapport[] | null;
}

export class Categorie implements ICategorie {
  constructor(public id?: number, public categorie?: string | null, public rapports?: IRapport[] | null) {}
}

export function getCategorieIdentifier(categorie: ICategorie): number | undefined {
  return categorie.id;
}
