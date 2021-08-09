export interface IMail {
  id?: number;
  typeMail?: string | null;
  msgMail?: string | null;
}

export class Mail implements IMail {
  constructor(public id?: number, public typeMail?: string | null, public msgMail?: string | null) {}
}

export function getMailIdentifier(mail: IMail): number | undefined {
  return mail.id;
}
