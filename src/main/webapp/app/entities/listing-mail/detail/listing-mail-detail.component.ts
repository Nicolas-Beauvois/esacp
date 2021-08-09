import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IListingMail } from '../listing-mail.model';

@Component({
  selector: 'jhi-listing-mail-detail',
  templateUrl: './listing-mail-detail.component.html',
})
export class ListingMailDetailComponent implements OnInit {
  listingMail: IListingMail | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ listingMail }) => {
      this.listingMail = listingMail;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
