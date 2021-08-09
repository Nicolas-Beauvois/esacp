export interface IListingMail {
  id?: number;
  typeMessage?: string | null;
  email?: string | null;
}

export class ListingMail implements IListingMail {
  constructor(public id?: number, public typeMessage?: string | null, public email?: string | null) {}
}

export function getListingMailIdentifier(listingMail: IListingMail): number | undefined {
  return listingMail.id;
}
