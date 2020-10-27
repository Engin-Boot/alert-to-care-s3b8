import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule, routingComponents  } from './app-routing.module';
import { AppComponent } from './app.component';
import { OccupancyComponent } from './occupancy-component/occupancy-component.component';
import { CareComponent } from './care-component/care-component.component';
import { FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {ClientService} from './service/app.service';

@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
    OccupancyComponent,
    CareComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,FormsModule,HttpClientModule
  ],
  providers: [
            {provide:'apiBaseAddress',useValue:'http://localhost:8081'},
            {provide:'clientService',useClass:ClientService}
              ],
  bootstrap: [AppComponent]
})
export class AppModule { }
