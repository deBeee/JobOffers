package com.junioroffers.domain.offer;

import lombok.Builder;

@Builder
record Offer(String id, String title, String company, String salary, String url) {
}
