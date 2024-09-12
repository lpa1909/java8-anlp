package org.planning.SpringBootProject.pagination;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.planning.SpringBootProject.model.ProductInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public class PaginationResult<E>{
	private int totalRecords;
	private int currentPage;
	private List<E> list;
	private int maxResult;
	private int totalPages;

	private int maxNavigationPage;

	private List<Integer> navigationPages;

	private SessionFactory sessionFactory;

	private E entity;

	// @page: 1, 2, ..
	public PaginationResult(Query<E> query, int page, int maxResult, int maxNavigationPage) {
		final int pageIndex = page - 1 < 0 ? 0 : page - 1;

		int fromRecordIndex = pageIndex * maxResult;
		int maxRecordIndex = fromRecordIndex + maxResult;

		ScrollableResults resultScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);

		List<E> results = new ArrayList<>();

		boolean hasResult = resultScroll.first();

		if (hasResult) {
			hasResult = resultScroll.scroll(fromRecordIndex);

			if (hasResult) {
				do {
					E record = (E) resultScroll.get();
					results.add(record);
				} while (resultScroll.next()//
						&& resultScroll.getRowNumber() >= fromRecordIndex
						&& resultScroll.getRowNumber() < maxRecordIndex);

			}

		
			resultScroll.last();
		}


		this.totalRecords = resultScroll.getRowNumber() + 1;
		this.currentPage = pageIndex + 1;
		this.list = results;
		this.maxResult = maxResult;

		if (this.totalRecords % this.maxResult == 0) {
			this.totalPages = this.totalRecords / this.maxResult;
		} else {
			this.totalPages = (this.totalRecords / this.maxResult) + 1;
		}

		this.maxNavigationPage = maxNavigationPage;

		if (maxNavigationPage < totalPages) {
			this.maxNavigationPage = maxNavigationPage;
		}

		this.calcNavigationPages();
	}

	public PaginationResult(ScrollableResults resultScroll, int page, int maxResult, int maxNavigationPage) {
		final int pageIndex = page - 1 < 0 ? 0 : page - 1;

		int fromRecordIndex = pageIndex * maxResult;
		int maxRecordIndex = fromRecordIndex + maxResult;

		List<E> results = new ArrayList<>();

		boolean hasResult = resultScroll.first();

		if (hasResult) {
			hasResult = resultScroll.scroll(fromRecordIndex);

			if (hasResult) {
				do {
					E record = (E) resultScroll.get();
					results.add(record);
				} while (resultScroll.next()//
						&& resultScroll.getRowNumber() >= fromRecordIndex
						&& resultScroll.getRowNumber() < maxRecordIndex);

			}


			resultScroll.last();
		}


		this.totalRecords = resultScroll.getRowNumber() + 1;
		this.currentPage = pageIndex + 1;
		this.list = results;
		this.maxResult = maxResult;

		if (this.totalRecords % this.maxResult == 0) {
			this.totalPages = this.totalRecords / this.maxResult;
		} else {
			this.totalPages = (this.totalRecords / this.maxResult) + 1;
		}

		this.maxNavigationPage = maxNavigationPage;

		if (maxNavigationPage < totalPages) {
			this.maxNavigationPage = maxNavigationPage;
		}

		this.calcNavigationPages();
	}

	public PaginationResult(SessionFactory sessionFactory, String sql,Class<E> eClass, int page, int maxResult, int maxNavigationPage) {
		final int pageIndex = page - 1 < 0 ? 0 : page - 1;

		int fromRecordIndex = pageIndex * maxResult;
		int maxRecordIndex = fromRecordIndex + maxResult;
		this.sessionFactory = sessionFactory;
		Query<E> query;
		ScrollableResults resultScroll = null;
		try (Session session = this.sessionFactory.getCurrentSession()) {
			query = session.createQuery(sql, eClass);
			resultScroll = query.scroll();
		}

		while (resultScroll.next()) {
			E employee = (E) resultScroll.get();
			// ... xử lý employee
		}



		List<E> results = new ArrayList<>();

		boolean hasResult = resultScroll.first();

		if (hasResult) {
			hasResult = resultScroll.scroll(fromRecordIndex);

			if (hasResult) {
				do {
					E record = (E) resultScroll.get();
					results.add(record);
				} while (resultScroll.next()//
						&& resultScroll.getRowNumber() >= fromRecordIndex
						&& resultScroll.getRowNumber() < maxRecordIndex);

			}


			resultScroll.last();
		}


		this.totalRecords = resultScroll.getRowNumber() + 1;
		this.currentPage = pageIndex + 1;
		this.list = results;
		this.maxResult = maxResult;

		if (this.totalRecords % this.maxResult == 0) {
			this.totalPages = this.totalRecords / this.maxResult;
		} else {
			this.totalPages = (this.totalRecords / this.maxResult) + 1;
		}

		this.maxNavigationPage = maxNavigationPage;

		if (maxNavigationPage < totalPages) {
			this.maxNavigationPage = maxNavigationPage;
		}

		this.calcNavigationPages();
	}

	private void calcNavigationPages() {

		this.navigationPages = new ArrayList<Integer>();

		int current = this.currentPage > this.totalPages ? this.totalPages : this.currentPage;

		int begin = current - this.maxNavigationPage / 2;
		int end = current + this.maxNavigationPage / 2;

	
		navigationPages.add(1);
		if (begin > 2) {


			navigationPages.add(-1);
		}

		for (int i = begin; i < end; i++) {
			if (i > 1 && i < this.totalPages) {
				navigationPages.add(i);
			}
		}

		if (end < this.totalPages - 2) {

		
			navigationPages.add(-1);
		}
	
		navigationPages.add(this.totalPages);
	}



	public int getTotalPages() {
		return totalPages;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<E> getList() {
		return list;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public List<Integer> getNavigationPages() {
		return navigationPages;
	}
}